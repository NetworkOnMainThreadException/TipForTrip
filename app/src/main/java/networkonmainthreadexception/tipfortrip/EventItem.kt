package networkonmainthreadexception.tipfortrip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class EventItem(
    val eventTitle: String = "",
    val members: List<String> = emptyList(),
    val goods: List<String> = emptyList(),
    val date: Date = Date(0),
    val routeId: String = ""
) : Parcelable, Model()
