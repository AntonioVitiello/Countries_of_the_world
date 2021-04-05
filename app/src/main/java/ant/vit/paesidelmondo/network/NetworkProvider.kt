package ant.vit.paesidelmondo.network

import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Vitiello Antonio
 */
class NetworkProvider @Inject constructor(private val apiService: ApiService) {

    fun getAllWithFieldsFilterSingle(fields: String): Single<CountriesResponse> {
        return apiService.getAllWithFieldsFilterSingle(fields)
    }

    fun getCountryDetailsSingle(countryName: String): Single<DetailsResponse> {
        return apiService.getCountryDetailsSingle(countryName)
    }

}