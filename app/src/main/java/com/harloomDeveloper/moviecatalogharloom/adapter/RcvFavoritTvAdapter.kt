package com.harloomDeveloper.moviecatalogharloom.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import kotlinx.android.synthetic.main.item_favorit.view.*

class RcvFavoritTvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ETv>() {

        override fun areItemsTheSame(oldItem: ETv, newItem: ETv): Boolean  =oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ETv, newItem: ETv): Boolean  = oldItem == newItem
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MovieFavoritHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorit,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieFavoritHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ETv>) {
        differ.submitList(list)
    }

    class MovieFavoritHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ETv) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.action_delete.setOnClickListener {
                interaction?.onDeleteTap(adapterPosition, item)
            }

            itemView.title_favorit.text = item.title
            try {
                Glide.with(context)
                    .load(Constant.BASE_IMAGE+item.posterPath)
                    .into(itemView.iv_thumbail)
            }catch (e : Exception){

            }


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ETv)
        fun onDeleteTap(position: Int, item: ETv)
    }
}

