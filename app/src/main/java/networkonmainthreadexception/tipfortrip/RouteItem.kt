package networkonmainthreadexception.tipfortrip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class RouteItem(
    val title: String = "",
    var imageUrl: String = "",
    val previewText: String = "",
    val fullText: String = "",
    val location: String = "",
    val publishDate: Date = Date(0),
    var pointsId: String = ""
) : Parcelable, Model()
