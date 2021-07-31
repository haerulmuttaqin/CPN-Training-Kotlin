package id.haerulmuttaqin.cpn_training_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.haerulmuttaqin.cpn_training_kotlin.R
import id.haerulmuttaqin.cpn_training_kotlin.databinding.ItemCommentBinding
import id.haerulmuttaqin.cpn_training_kotlin.toPrettyTime
import id.haerulmuttaqin.entity.PostComment

class CommentAdapter: ListAdapter<PostComment, CommentAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostComment) {
            binding.apply {
                ownerPicture.load(item.postOwner?.picture) {
                    crossfade(true)
                    placeholder(R.drawable.gray_100_bg)
                }
                ownerName.text = "${item.postOwner?.firstName} ${item.postOwner?.lastName}"
                commentText.text = item.message
                commentDate.text = toPrettyTime(item.publishDate)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PostComment>() {
            override fun areItemsTheSame(
                oldItem: PostComment,
                newItem: PostComment
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PostComment,
                newItem: PostComment
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}