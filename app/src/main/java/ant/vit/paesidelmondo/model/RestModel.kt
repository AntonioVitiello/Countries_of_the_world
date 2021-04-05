package ant.vit.paesidelmondo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Vitiello Antonio
 */
@Parcelize
data class RestModel(
    var id: Int? = null,
    var userId: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var publishDate: Date? = null,
    var publishDateString: String? = null
) : Parcelable