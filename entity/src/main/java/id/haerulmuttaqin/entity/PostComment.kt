package id.haerulmuttaqin.entity

import com.squareup.moshi.Json

data class PostComment(
	@Json(name="id") val id: String? = null,
	@Json(name="owner") val postOwner: PostOwner? = null,
	@Json(name="publishDate") val publishDate: String? = null,
	@Json(name="message") val message: String? = null
)