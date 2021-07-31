package id.haerulmuttaqin.entity

import com.squareup.moshi.Json

data class PostOwner(
	@Json(name="id") val id: String? = null,
	@Json(name="firstName") val firstName: String? = null,
	@Json(name="lastName") val lastName: String? = null,
	@Json(name="title") val title: String? = null,
	@Json(name="email") val email: String? = null,
	@Json(name="picture") val picture: String? = null
)
