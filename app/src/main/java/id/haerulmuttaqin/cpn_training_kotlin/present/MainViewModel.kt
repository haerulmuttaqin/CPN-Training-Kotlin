package id.haerulmuttaqin.cpn_training_kotlin.present

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.haerulmuttaqin.domain.MainUseCase
import id.haerulmuttaqin.entity.PostComment
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.flow.Flow

class MainViewModel constructor(private val mainUseCase: MainUseCase) : ViewModel() {
    val posts: Flow<PagingData<PostItem>> = mainUseCase.getPosts().cachedIn(viewModelScope)
    fun getPostBy(id: String): LiveData<PostItem> = mainUseCase.getPostBy(id)
    fun getPostCommentBy(postId: String) = mainUseCase.getComments(postId).asLiveData()
}