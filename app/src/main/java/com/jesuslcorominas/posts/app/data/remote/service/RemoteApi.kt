package com.jesuslcorominas.posts.app.data.remote.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("users/{id}")
    fun getAuthor(@Path("id") userId: Int): Call<Author>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>
}