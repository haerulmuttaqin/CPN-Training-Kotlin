package id.haerulmuttaqin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostRemoteKeys(@PrimaryKey val id: String, val prevKey: Int?, val nextKey: Int?)
