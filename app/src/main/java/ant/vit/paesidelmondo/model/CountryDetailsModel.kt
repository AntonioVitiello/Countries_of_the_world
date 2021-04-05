package ant.vit.paesidelmondo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vitiello Antonio
 */
@Parcelize
data class CountryDetailsModel(var countryName: String) : Parcelable {
    var alpha2Code: String? = null
    var nativeName: String? = null
    var flagUrl: String? = null
    var capital: String? = null
    var altSpellings = ""
    var region: String? = null
    var languages = ""
    var currencies = ""
}