package id.haerulmuttaqin.domain

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.haerulmuttaqin.domain.source.MainMediator
import id.haerulmuttaqin.entity.PostComment
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.flow.Flow

class MainUseCaseImpl constructor(private val repository: MainRepository): MainUseCase {
    
    @ExperimentalPagingApi
    override fun getPosts(): Flow<PagingData<PostItem>> = Pager (
        config = PagingConfig(pageSize = BuildConfig.PAGE_SIZE, enablePlaceholders = true),
        pagingSourceFactory = { repository.getAllPost() },
        remoteMediator = MainMediator(repository)
    ).flow

    override fun getComments(postId: String): Flow<ResultState<List<PostComment>>> = repository.getComment(postId)

    override fun getPostBy(id: String): LiveData<PostItem> = repository.getPostBy(id)
}