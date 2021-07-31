package id.haerulmuttaqin.cpn_training_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.haerulmuttaqin.cpn_training_kotlin.R
import id.haerulmuttaqin.cpn_training_kotlin.databinding.ItemMainBinding
import id.haerulmuttaqin.cpn_training_kotlin.toPrettyTime
import id.haerulmuttaqin.entity.PostItem

class MainPostAdapter constructor(private val onItemClick: (PostItem?) -> Unit) :
    PagingDataAdapter<PostItem, MainPostAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context)), onItemClick)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(item = getItem(position)!!)
    }

    class ListItemViewHolder(
        private var binding: ItemMainBinding,
        private val onItemClick: (PostItem?) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostItem) {
            binding.apply {
                image.load(item.image) {
                    crossfade(true)
                    placeholder(R.drawable.gray_100_bg)
                }
                ownerPicture.load(item.postOwner?.picture) {
                    crossfade(true)
                    placeholder(R.drawable.gray_100_bg)
                }
                ownerName.text = "${item.postOwner?.firstName} ${item.postOwner?.lastName}"
                ownerEmail.text = item.postOwner?.email
                itemTitle.text = item.text
                publishDate.text = toPrettyTime(item.publishDate)
                likeCount.text = "${item.likes.toString()} Likes"
                postItem.setOnClickListener { onItemClick(item) }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PostItem>() {
            override fun areItemsTheSame(
                oldItem: PostItem,
                newItem: PostItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PostItem,
                newItem: PostItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}
