package com.jesuslcorominas.posts.app.data.remote.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteService(private val baseUrl: String) {

    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient
            .Builder()
            .addInterceptor(this)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    private val remoteApi: RemoteApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create<RemoteApi>(
                RemoteApi::class.java
            )
        }

    fun remoteApi() = remoteApi
}