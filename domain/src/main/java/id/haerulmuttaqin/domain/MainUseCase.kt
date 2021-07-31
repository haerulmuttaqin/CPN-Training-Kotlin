package id.haerulmuttaqin.domain

 import androidx.lifecycle.LiveData
 import androidx.paging.PagingData
 import id.haerulmuttaqin.entity.PostComment
 import id.haerulmuttaqin.entity.PostItem
 import id.haerulmuttaqin.entity.ResultState
 import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getPosts(): Flow<PagingData<PostItem>>
    fun getComments(postId: String): Flow<ResultState<List<PostComment>>>
    fun getPostBy(id: String): LiveData<PostItem>
}