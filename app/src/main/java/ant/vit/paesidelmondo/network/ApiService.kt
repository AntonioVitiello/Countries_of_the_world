package ant.vit.paesidelmondo.network

import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Vitiello Antonio
 */
interface ApiService {

    //https://restcountries.eu/rest/v2/all?fields=name;capital;currencies;alpha2Code
    @GET("v2/all")
    fun getAllWithFieldsFilterSingle(@Query("fields") fields: String): Single<CountriesResponse>

    //https://restcountries.eu/rest/v2/name/andorra?fullText=true
    @GET("v2/name/{fullCountryName}?fullText=true")
    fun getCountryDetailsSingle(@Path("fullCountryName") fullCountryName: String): Single<DetailsResponse>

}