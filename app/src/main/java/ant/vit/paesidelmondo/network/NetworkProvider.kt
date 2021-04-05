package ant.vit.paesidelmondo.network

import android.util.Log
import ant.vit.paesidelmondo.BuildConfig
import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Vitiello Antonio
 */
class NetworkProvider {

    init {
        //default error handler to avoid app crash
        RxJavaPlugins.setErrorHandler { t: Throwable -> Log.e(TAG, "RxJava plugin error", t) }
    }

    companion object {
        const val TAG = "NetworkProvider"
        const val ENDPOINT = "https://restcountries.eu/rest/"

        private val httpClient = OkHttpClient.Builder().apply {
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()

        private var apiService = Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .callbackExecutor(Executors.newCachedThreadPool())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)
    }

    fun getAllWithFieldsFilterSingle(fields: String): Single<CountriesResponse> {
        return apiService.getAllWithFieldsFilterSingle(fields)
    }

    fun getCountryDetailsSingle(countryName: String): Single<DetailsResponse> {
        return apiService.getCountryDetailsSingle(countryName)
    }

}