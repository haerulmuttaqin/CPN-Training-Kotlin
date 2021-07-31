package id.haerulmuttaqin.domain.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.haerulmuttaqin.domain.BuildConfig
import id.haerulmuttaqin.domain.MainRepository
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.flow.collect

class MainPagingSource constructor(private val repository: MainRepository):
    PagingSource<Int, PostItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostItem> {
        return try {
            val mutableListMovies = mutableListOf<PostItem>()
            val nextPageNumber = params.key ?: BuildConfig.DEFAULT_PAGE_INDEX
            val nextPrevNumber = if (nextPageNumber == BuildConfig.DEFAULT_PAGE_INDEX) null else nextPageNumber - 1
            val response = repository.getPost(page = nextPageNumber)
            var throwable = Throwable()
            response.collect { news ->
                when(news) {
                    is ResultState.Success -> mutableListMovies.addAll(news.data)
                    is ResultState.Error -> throwable = news.throwable
                    is ResultState.Empty -> Log.e("DataSource", "Empty")
                }
            }
            
            if (throwable.message != null) {
                return LoadResult.Error(throwable)
            }
            return LoadResult.Page(
                data = mutableListMovies,
                prevKey = nextPrevNumber,
                nextKey = nextPageNumber + 1
            )

        } catch (t: Throwable) {
            t.printStackTrace()
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostItem>): Int? {
        return null
    }
}