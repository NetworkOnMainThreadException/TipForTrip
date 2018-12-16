package networkonmainthreadexception.tipfortrip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
class RouteItem(
    val title: String,
    val imageUrl: String,
    val previewText: String,
    val fullText: String,
    val location: String,
    val publishDate: Date
) : Parcelable
