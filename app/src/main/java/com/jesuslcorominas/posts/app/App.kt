package com.jesuslcorominas.posts.app

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.facebook.stetho.Stetho
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.di.ApplicationModule
import com.jesuslcorominas.posts.app.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        Stetho.initializeWithDefaults(this);

        val fontRequest = FontRequest(
            "com.example.fontprovider",
            "com.example",
            "emoji compat Font Query",
            R.array.com_google_android_gms_fonts_certs
        )
        val config = FontRequestEmojiCompatConfig(this, fontRequest)
        EmojiCompat.init(config)
    }
}