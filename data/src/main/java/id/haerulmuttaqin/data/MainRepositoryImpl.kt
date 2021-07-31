package id.haerulmuttaqin.data

import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import id.haerulmuttaqin.data.local.AppDatabase
import id.haerulmuttaqin.domain.MainRepository
import id.haerulmuttaqin.entity.PostComment
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.PostRemoteKeys
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class MainRepositoryImpl constructor(
    private val apiServices: ApiServices,
    private val appDatabase: AppDatabase
) : MainRepository {
    override fun getPost(page: Int): Flow<ResultState<List<PostItem>>> = flow {
        try {
            val response = apiServices.getPosts(page = page)
            if (response.data.isNotEmpty()) {
                emit(ResultState.Success(response.data))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getComment(postId: String): Flow<ResultState<List<PostComment>>> = flow {
        try {
            val response = apiServices.getComment(postId = postId)
            if (response.data.isNotEmpty()) {
                emit(ResultState.Success(response.data))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getDatabase(): RoomDatabase = appDatabase

    override fun getAllPost() = appDatabase.postItemDao().getAllPost()
    
    override fun getPostBy(id: String): LiveData<PostItem> = appDatabase.postItemDao().getPostBy(id)

    override suspend fun insertAllPost(postItem: List<PostItem>) {
        withContext(Dispatchers.IO) { appDatabase.postItemDao().insertAllPost(postItem) }
    }

    override suspend fun clearAllPost() {
        withContext(Dispatchers.IO) { appDatabase.postItemDao().clearAllPost() }
    }

    override suspend fun insertAllRemoteKeys(postRemoteKeys: List<PostRemoteKeys>) {
        withContext(Dispatchers.IO) {
            appDatabase.remoteKeysDao().insertAllRemoteKeys(postRemoteKeys)
        }
    }

    override suspend fun remoteKeysBy(url: String): PostRemoteKeys? =
        appDatabase.remoteKeysDao().remoteKeysBy(url)

    override suspend fun clearRemoteKeys() {
        withContext(Dispatchers.IO) { appDatabase.remoteKeysDao().clearRemoteKeys() }
    }
}