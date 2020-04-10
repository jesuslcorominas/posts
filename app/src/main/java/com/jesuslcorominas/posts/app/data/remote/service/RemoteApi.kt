package com.jesuslcorominas.posts.app.data.remote.service

import retrofit2.Call
import retrofit2.http.GET

interface RemoteApi {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

}