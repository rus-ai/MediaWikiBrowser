package ru.wtw.mediawikibrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.wtw.mediawikibrowser.ViewType.Companion.fromValue

enum class ViewType(val type: Int) {
    TITLE_TYPE(0), DATA_TYPE(1);

    companion object {
        fun fromValue(type: Int): ViewType {
            return when (type) {
                0 -> TITLE_TYPE
                1 -> DATA_TYPE
                else -> throw IllegalArgumentException("$type is not a valid value for ViewType")
            }
        }
    }
}

class MediaWikiAdapter(private val dataSet: List<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if ( position % 2 == 0 ) {
            ViewType.TITLE_TYPE.type
        } else {
            ViewType.DATA_TYPE.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (fromValue(viewType)) {
            ViewType.TITLE_TYPE -> MyTitleViewHolder(inflater.inflate(R.layout.title_view, parent, false))
            ViewType.DATA_TYPE -> MyDataViewHolder(inflater.inflate(R.layout.data_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyTitleViewHolder -> holder.bindTitle(dataSet[position])
            is MyDataViewHolder -> holder.bindData(dataSet[position])
        }
    }

    override fun getItemCount(): Int = dataSet.size
}

class MyTitleViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindTitle(title: String) {
        (itemView as TextView).text = title
    }
}

class MyDataViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindData(data: String) {
        (itemView as TextView).text = data
    }

}