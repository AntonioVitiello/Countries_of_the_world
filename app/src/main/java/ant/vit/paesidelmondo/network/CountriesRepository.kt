package ant.vit.paesidelmondo.network

import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Vitiello Antonio
 */
class CountriesRepository @Inject constructor(private val networkProvider: NetworkProvider) {

    companion object {
        private const val COUNTRY_NAME_FILTER = "name;alpha2Code;languages"
    }

    fun getAllCountriesNameSingle(): Single<CountriesResponse> {
        return networkProvider.getAllWithFieldsFilterSingle(COUNTRY_NAME_FILTER)
    }

    fun getCountryDetailsSingle(countryName: String): Single<DetailsResponse> {
        return networkProvider.getCountryDetailsSingle(countryName)
    }

}