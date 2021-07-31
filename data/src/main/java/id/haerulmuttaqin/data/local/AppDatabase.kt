package id.haerulmuttaqin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.haerulmuttaqin.entity.PostItem
import id.haerulmuttaqin.entity.PostRemoteKeys
import id.haerulmuttaqin.entity.typeconverter.PostOwnerTypeConverter
import id.haerulmuttaqin.entity.typeconverter.TagTypeConverter

@Database(entities = [PostItem::class, PostRemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(PostOwnerTypeConverter::class, TagTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postItemDao(): PostItemDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}