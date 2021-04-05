package ant.vit.paesidelmondo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vitiello Antonio
 */
@Parcelize
data class CountryNameModel(var countryName: String) : Parcelable {
    var alpha2Code: String? = null
    var languages: List<String>? = null
}