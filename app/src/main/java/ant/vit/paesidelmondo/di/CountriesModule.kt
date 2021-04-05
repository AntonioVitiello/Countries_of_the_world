package ant.vit.paesidelmondo.di

import ant.vit.paesidelmondo.BuildConfig
import ant.vit.paesidelmondo.network.ApiService
import ant.vit.paesidelmondo.network.CountriesRepository
import ant.vit.paesidelmondo.network.NetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
@Module
@InstallIn(ActivityComponent::class)
object CountriesModule {

    private const val ENDPOINT = "https://restcountries.eu/rest/"
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

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .callbackExecutor(Executors.newCachedThreadPool())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideNetworkProvider(apiService: ApiService): NetworkProvider = NetworkProvider(apiService)

    @Provides
    fun provideCountriesRepository(networkProvider: NetworkProvider): CountriesRepository = CountriesRepository(networkProvider)

}