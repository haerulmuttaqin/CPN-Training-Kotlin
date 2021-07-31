package id.haerulmuttaqin.domain.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.haerulmuttaqin.domain.BuildConfig
import id.haerulmuttaqin.domain.MainRepository
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.PostRemoteKeys
import id.haerulmuttaqin.entity.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MainMediator constructor(private val repository: MainRepository) :
    RemoteMediator<Int, PostItem>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PostItem>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            var throwable = Throwable()
            var isEndOfList = false
            val response = repository.getPost(page = page)
            response.collect { news ->
                Log.e("DataSource", "$news")
                when (news) {
                    is ResultState.Success -> {
                        isEndOfList = news.data.isEmpty()
                        repository.getDatabase().withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                repository.clearRemoteKeys()
                                repository.clearAllPost()
                            }
                            val prevKey =
                                if (page == BuildConfig.DEFAULT_PAGE_INDEX) null else page - 1
                            val nextKey = if (isEndOfList) null else page + 1
                            val keys = news.data.map { postItem ->
                                PostRemoteKeys(
                                    id = postItem.id,
                                    prevKey = prevKey,
                                    nextKey = nextKey
                                )
                            }
                            repository.insertAllRemoteKeys(keys)
                            repository.insertAllPost(news.data)
                        }
                    }
                    is ResultState.Error -> throwable = news.throwable 
                    ResultState.Empty -> Log.e("Mediator", "load: empty")
                }
            }
            return if (throwable.message != null) {
                MediatorResult.Error(throwable)
            } else {
                MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, PostItem>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: BuildConfig.DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys: PostRemoteKeys? = 
                    flowOf(getLastRemoteKey(state)).flowOn(Dispatchers.IO).single()
                remoteKeys?.nextKey ?: return 0
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys: PostRemoteKeys? =
                    flowOf(getFirstRemoteKey(state)).flowOn(Dispatchers.IO).single()

                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, PostItem>): PostRemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { post -> repository.remoteKeysBy(post.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, PostItem>): PostRemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { post -> repository.remoteKeysBy(post.id) }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, PostItem>): PostRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { url ->
                repository.remoteKeysBy(url)
            }
        }
    }

}