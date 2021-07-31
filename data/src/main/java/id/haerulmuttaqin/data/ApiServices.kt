package id.haerulmuttaqin.data

import id.haerulmuttaqin.entity.PostCommentResponse
import id.haerulmuttaqin.entity.PostResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    
    @GET("post")
    suspend fun getPosts(
        @Header("app-id") appId: String = BuildConfig.APP_ID,
        @Query("page") page: Int,
        @Query("limit") limit: Int = BuildConfig.PAGE_SIZE,
    ): PostResponse

    @GET("post/{post-id}/comment")
    suspend fun getComment(
        @Header("app-id") appId: String = BuildConfig.APP_ID,
        @Path("post-id") postId: String,
    ): PostCommentResponse
}