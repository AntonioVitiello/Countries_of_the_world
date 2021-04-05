package ant.vit

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


/**
 * Created by Antonio Vitiello on 05/04/2021.
 */
class CountriesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initPicasso()
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this).apply {
            downloader(OkHttp3Downloader(this@CountriesApplication, Long.MAX_VALUE))
        }.build().apply {
            setIndicatorsEnabled(true)
            isLoggingEnabled = true
        }
        Picasso.setSingletonInstance(picasso)
    }
}