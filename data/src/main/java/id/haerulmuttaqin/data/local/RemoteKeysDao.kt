package id.haerulmuttaqin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.haerulmuttaqin.entity.PostRemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKey: List<PostRemoteKeys>)

    @Query("SELECT * FROM PostRemoteKeys WHERE id = :id")
    suspend fun remoteKeysBy(id: String): PostRemoteKeys?

    @Query("DELETE FROM PostRemoteKeys")
    suspend fun clearRemoteKeys()
}

