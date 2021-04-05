package ant.vit.paesidelmondo.network

import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse
import io.reactivex.Single

/**
 * Created by Vitiello Antonio
 */
object CountriesRepository {
    private var networkProvider = NetworkProvider()
    private const val COUNTRY_NAME_FILTER = "name;alpha2Code;languages"

    fun getAllCountriesNameSingle(): Single<CountriesResponse> {
        return networkProvider.getAllWithFieldsFilterSingle(COUNTRY_NAME_FILTER)
    }

    fun getCountryDetailsSingle(countryName: String): Single<DetailsResponse> {
        return networkProvider.getCountryDetailsSingle(countryName)
    }

}