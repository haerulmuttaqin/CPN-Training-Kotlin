package id.haerulmuttaqin.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.haerulmuttaqin.entity.PostItem

@Dao
interface PostItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(doggoModel: List<PostItem>)

    @Query("SELECT * FROM PostItem")
    fun getAllPost(): PagingSource<Int, PostItem>

    @Query("SELECT * FROM PostItem WHERE id = :id")
    fun getPostBy(id: String): LiveData<PostItem>

    @Query("DELETE FROM PostItem")
    suspend fun clearAllPost()
}