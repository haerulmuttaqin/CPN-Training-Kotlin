package id.haerulmuttaqin.cpn_training_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.haerulmuttaqin.cpn_training_kotlin.R
import id.haerulmuttaqin.cpn_training_kotlin.databinding.LoaderStateBinding

class LoaderStateAdapter constructor(private val retry: () -> Unit): 
    LoadStateAdapter<LoaderStateAdapter.LoaderStateViewHolder>() {
    
    override fun onBindViewHolder(holder: LoaderStateViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderStateViewHolder {
        return LoaderStateViewHolder.create(parent, retry)
    }

    class LoaderStateViewHolder constructor(
        private val binding: LoaderStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoaderStateViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.loader_state, parent, false)
                val binding = LoaderStateBinding.bind(view)
                return LoaderStateViewHolder(binding, retry)
            }
        }
    }
}