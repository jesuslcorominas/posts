package com.jesuslcorominas.posts.app

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.facebook.stetho.Stetho
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.di.ApplicationModule
import com.jesuslcorominas.posts.app.di.DaggerApplicationComponent
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.IOException
import java.net.SocketException


class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()

        initTimber()
        initStetho()

        initEmojiCompat()

        configRxErrorHandle()
    }

    private fun initDagger() {
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun initStetho() = Stetho.initializeWithDefaults(this)

    private fun initEmojiCompat() {
        val config: EmojiCompat.Config
        // Use a downloadable font for EmojiCompat
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )

        config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
            .setReplaceAll(true)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    Timber.i("EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Timber.e(throwable, "EmojiCompat initialization failed")
                }
            })


        EmojiCompat.init(config)
    }

    private fun configRxErrorHandle() {
        RxJavaPlugins.setErrorHandler {
            val message: String =
                if ((it is IOException) || (it is SocketException)) {
                    "irrelevant network problem or API that throws on cancellation"
                } else if (it is InterruptedException) {
                    "some blocking code was interrupted by a dispose call"
                } else if ((it is NullPointerException) || (it is IllegalArgumentException)) {
                    "that's likely a bug in the application"
                } else if (it is IllegalStateException) {
                    "that's a bug in RxJava or in a custom operator"
                } else if (it is UndeliverableException) {
                    "Undeliverable exception received, not sure what to do"
                } else {
                    "Unknow error"
                }

            Timber.e(it, "Recibido error de Rx: $message")
        }
    }
}