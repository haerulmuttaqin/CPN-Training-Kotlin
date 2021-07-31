package id.haerulmuttaqin.entity

import com.squareup.moshi.Json

data class PostResponse(
	@Json(name="data") val data: List<PostItem>,
	@Json(name="total") val total: Int? = null,
	@Json(name="offset") val offset: Int? = null,
	@Json(name="limit") val limit: Int? = null,
	@Json(name="page") val page: Int? = null
    /*@Json(name="Response")
    val data: List<PostItem>*/
)
