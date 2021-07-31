package id.haerulmuttaqin.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.RoomDatabase
import id.haerulmuttaqin.entity.PostComment
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.PostRemoteKeys
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getPost(page: Int): Flow<ResultState<List<PostItem>>>
    fun getComment(postId: String): Flow<ResultState<List<PostComment>>>
    fun getDatabase(): RoomDatabase
    fun getAllPost(): PagingSource<Int, PostItem>
    fun getPostBy(id: String): LiveData<PostItem>
    suspend fun insertAllPost(postItem: List<PostItem>)
    suspend fun clearAllPost()
    suspend fun insertAllRemoteKeys(postItem: List<PostRemoteKeys>)
    suspend fun remoteKeysBy(url: String): PostRemoteKeys?
    suspend fun clearRemoteKeys()
}