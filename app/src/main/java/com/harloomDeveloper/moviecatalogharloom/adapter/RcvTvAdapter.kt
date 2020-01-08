package com.harloomDeveloper.moviecatalogharloom.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.TvShow
import kotlinx.android.synthetic.main.item_movie.view.*

class RcvTvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {

        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
           return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tv_show,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<TvShow>) {
        differ.submitList(list)
    }

    class MovieHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TvShow) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)

            }

            itemView.tv_title.text = item.title
            itemView.tv_tahun.text = "(${item.tahunRilis})"
            itemView.thumbail.setImageResource(item.thumbails!!)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TvShow)
    }
}

