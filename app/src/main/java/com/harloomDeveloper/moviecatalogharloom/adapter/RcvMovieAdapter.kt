package com.harloomDeveloper.moviecatalogharloom.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.MainViewModel
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_movie.view.*

class RcvMovieAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultMovie>() {
        override fun areItemsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ResultMovie, newItem: ResultMovie): Boolean = oldItem==newItem
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
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

    fun submitList(list: List<ResultMovie>) {
        differ.submitList(list)
    }

    class MovieHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ResultMovie) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_title.text = item.title
            itemView.tv_tahun.text = item.releaseDate
//            itemView.thumbail.setImageResource(item.!!)
            itemView.favorit_button.isLiked = item.isFavorit
            itemView.favorit_button.setOnLikeListener(object  : OnLikeListener{
                override fun liked(likeButton: LikeButton?) {
                    interaction?.onItemLike(item)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    interaction?.onItemUnlike(item)

                }
            })
            try {
                Glide.with(context)
                    .load(Constant.BASE_IMAGE+item.posterPath)
                    .into(itemView.thumbail)
            }catch (e : Exception){

            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ResultMovie)
        fun onItemLike(item: ResultMovie)
        fun onItemUnlike(item : ResultMovie)
    }
}

