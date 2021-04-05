package ant.vit

import android.app.Application
import android.util.Log
import ant.vit.paesidelmondo.BuildConfig
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins


/**
 * Created by Vitiello Antonio
 */

@HiltAndroidApp
class CountriesApplication : Application() {
    companion object {
        const val TAG = "PokemonApplication"
    }

    override fun onCreate() {
        super.onCreate()

        //RxJava default error handler to avoid app crash
        RxJavaPlugins.setErrorHandler { thr: Throwable -> Log.e(TAG, "Error on RxJava plugin.", thr) }

        if (BuildConfig.DEBUG) {
            initPicassoForDebug()
        }
    }

    private fun initPicassoForDebug() {
        val picasso = Picasso.Builder(this).apply {
            downloader(OkHttp3Downloader(this@CountriesApplication, Long.MAX_VALUE))
        }.build().apply {
            setIndicatorsEnabled(true)
            isLoggingEnabled = true
        }
        Picasso.setSingletonInstance(picasso)
    }
}