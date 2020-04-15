package com.jesuslcorominas.posts.app.data.remote.service

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts")
    fun getPostsRx(): Observable<List<Post>>

    @GET("users/{id}")
    fun getAuthor(@Path("id") userId: Int): Call<Author>

    @GET("users/{id}")
    fun getAuthorRx(@Path("id") userId: Int): Observable<Author>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>

    @GET("posts/{id}/comments")
    fun getCommentsRx(@Path("id") postId: Int): Observable<List<Comment>>
}