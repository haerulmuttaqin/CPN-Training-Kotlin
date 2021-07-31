package id.haerulmuttaqin.cpn_training_kotlin.present

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import id.haerulmuttaqin.cpn_training_kotlin.R
import id.haerulmuttaqin.cpn_training_kotlin.adapter.CommentAdapter
import id.haerulmuttaqin.cpn_training_kotlin.adapter.LoaderStateAdapter
import id.haerulmuttaqin.cpn_training_kotlin.adapter.MainPostAdapter
import id.haerulmuttaqin.cpn_training_kotlin.databinding.FragmentPostBinding
import id.haerulmuttaqin.entity.PostComment
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by sharedViewModel()
    private val safeArgs: PostFragmentArgs by navArgs()
    private val adapter: CommentAdapter = CommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.id
        viewModel.getPostBy(id).observe(viewLifecycleOwner ,{ item ->
            bind(item)
        })
        setupComment(id)
    }

    private fun setupComment(id: String) {
        viewModel.getPostCommentBy(id).observe(viewLifecycleOwner, { comment ->
            if (comment != null) {
                when(comment) {
                    is ResultState.Success -> onResultSuccess(comment.data)
                    is ResultState.Error -> onResultError(comment.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        })
        binding.recyclerView.adapter = adapter
    }
    
    private fun bind(item: PostItem) {
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
            publishDate.text = item.publishDate
            link.text = item.link
            likeCount.text = "${item.likes.toString()} Likes"
        }
    }

    private fun onResultEmpty() {
        binding.textError.isVisible = true
        binding.progressBar.isVisible = false
    }

    private fun onResultError(throwable: Throwable) {
        Log.i("PostFragment", "Error: $throwable")
        binding.textError.isVisible = true
        binding.progressBar.isVisible = false
        binding.textError.text = throwable.message
    }

    private fun onResultSuccess(movies: List<PostComment>) {
        Log.i("PostFragment", "Success: $movies")
        binding.textError.isVisible = false
        binding.progressBar.isVisible = false
        movies.let {
            adapter.submitList(movies)
        }
        binding.apply {
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}