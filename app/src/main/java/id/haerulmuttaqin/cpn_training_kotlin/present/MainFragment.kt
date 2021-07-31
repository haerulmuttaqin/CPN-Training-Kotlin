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
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.haerulmuttaqin.cpn_training_kotlin.adapter.LoaderStateAdapter
import id.haerulmuttaqin.cpn_training_kotlin.adapter.MainPostAdapter
import id.haerulmuttaqin.cpn_training_kotlin.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.log

@ExperimentalPagingApi
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private val adapter: MainPostAdapter by lazy {
        MainPostAdapter(
            onItemClick = { post ->
                findNavController().navigate(
                    MainFragmentDirections.actionMainNewsFragmentToPostFragment(post!!.id)
                )
            }
        )
    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.posts.collectLatest { post ->
                adapter.submitData(post)
            }
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.adapter = adapter.withLoadStateFooter(LoaderStateAdapter { adapter.retry() } )
        adapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                binding.errorMsg.text = it.error.message
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.GONE
            binding.retryButton.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
            binding.errorMsg.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}