package id.haerulmuttaqin.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class PostItem(
    @PrimaryKey
    @Json(name="id") val id: String,
    @Json(name="owner") val postOwner: PostOwner? = null,
    @Json(name="image") val image: String? = null,
    @Json(name="publishDate") val publishDate: String? = null,
    @Json(name="link") val link: String? = null,
    @Json(name="text") val text: String? = null,
    @Json(name="tags") val tags: List<String?>? = null,
    @Json(name="likes") val likes: Int? = null
   
   /* @Json(name="id")
    val id: String,

    @Json(name="width")
    val width: Int? = null,

    @Json(name="url")
    val url: String? = null,

    @Json(name="height")
    val height: Int? = null*/
)
