package ant.vit.paesidelmondo.model

import ant.vit.paesidelmondo.network.dto.all.CountriesResponse
import ant.vit.paesidelmondo.network.dto.details.DetailsResponse

/**
 * Created by Vitiello Antonio
 */
fun mapAllCountryNames(response: CountriesResponse): List<CountryNameModel> {
    return mutableListOf<CountryNameModel>().apply {
        response.forEach { country ->
            if (country.name != null) {
                add(CountryNameModel(country.name).apply {
                    alpha2Code = country.alpha2Code
                    languages = country.languages?.map { it.name ?: "nd" }
                })
            }
        }
    }
}

fun mapCountryDetails(response: DetailsResponse, countryName: String): CountryDetailsModel {
    return CountryDetailsModel(countryName).apply {
        response.forEach { details ->
            if (details.name != null) {
                this.countryName = details.name
                alpha2Code = details.alpha2Code
                nativeName = details.nativeName
                flagUrl = details.flag
                capital = details.capital
                region = details.region

                details.altSpellings?.forEach { altSpellings += " $it," }
                altSpellings = altSpellings.trimStart().trimEnd(',')


                details.languages?.forEach { languages += " ${it.name} (${it.nativeName})," }
                languages = languages.trimStart().trimEnd(',')

                details.currencies?.forEach { currencies += " ${it.name} (${it.symbol})," }
                currencies = currencies.trimStart().trimEnd(',')
            }
        }
    }
}
