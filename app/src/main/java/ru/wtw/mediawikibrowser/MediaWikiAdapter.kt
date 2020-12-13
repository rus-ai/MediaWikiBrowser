package ru.wtw.mediawikibrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.wtw.mediawikibrowser.ViewType.Companion.fromValue

enum class ViewType(val type: Int) {
    UPDATE_TYPE(0), ARTICLE_TYPE(1);

    companion object {
        fun fromValue(type: Int): ViewType {
            return when (type) {
                0 -> UPDATE_TYPE
                1 -> ARTICLE_TYPE
                else -> throw IllegalArgumentException("$type is not a valid value for ViewType")
            }
        }
    }
}

class MediaWikiAdapter(private val dataSet: List<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if ( position == dataSet.size ) {
            ViewType.UPDATE_TYPE.type
        } else {
            ViewType.ARTICLE_TYPE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (fromValue(viewType)) {
            ViewType.UPDATE_TYPE -> ServiceUpdateViewHolder(inflater.inflate(R.layout.update_view, parent, false))
            ViewType.ARTICLE_TYPE -> MediaWikiArticleViewHolder(inflater.inflate(R.layout.data_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ServiceUpdateViewHolder -> holder.bindUpdate()
            is MediaWikiArticleViewHolder -> holder.bindData(dataSet[position])
        }
    }

    override fun getItemCount(): Int = dataSet.size + 1
}

class ServiceUpdateViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindUpdate() {
        return
    }
}

class MediaWikiArticleViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindData(data: String) {
        (itemView as TextView).text = data
    }

}