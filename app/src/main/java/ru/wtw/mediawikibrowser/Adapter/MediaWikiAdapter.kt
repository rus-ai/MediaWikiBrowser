package ru.wtw.mediawikibrowser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.wtw.mediawikibrowser.Model.AllPages_old
import ru.wtw.mediawikibrowser.Model.ViewType
import ru.wtw.mediawikibrowser.Model.ViewType.Companion.fromValue
import ru.wtw.mediawikibrowser.R


class MediaWikiAdapter(private val context: Context, private val dataSet: MutableList<AllPages_old>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            is MediaWikiArticleViewHolder -> holder.bindData(dataSet[position].name.toString())
        }
    }

    override fun getItemCount(): Int = dataSet.size + 1

    class ServiceUpdateViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bindUpdate() {
            return
        }
    }

    class MediaWikiArticleViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bindData(name: String) {
            (itemView as TextView).text = name
            return
        }
    }

}

