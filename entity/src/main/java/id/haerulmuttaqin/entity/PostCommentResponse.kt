package id.haerulmuttaqin.entity

import com.squareup.moshi.Json

data class PostCommentResponse(
	@Json(name="data") val data: List<PostComment>,
	@Json(name="total") val total: Int? = null,
	@Json(name="offset") val offset: Int? = null,
	@Json(name="limit") val limit: Int? = null,
	@Json(name="page") val page: Int? = null
)
