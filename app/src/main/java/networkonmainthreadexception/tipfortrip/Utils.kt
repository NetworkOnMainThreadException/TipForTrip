package networkonmainthreadexception.tipfortrip

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot

val db by lazy { FirebaseFirestore.getInstance() }

inline fun <reified T : Model> DocumentSnapshot.tryToObjectWithId() =
    if (exists()) toObjectWithId<T>() else null

inline fun <reified T : Model> DocumentSnapshot.toObjectWithId() =
    toObject(T::class.java)!!.also { it.id = id }

inline fun <reified T : Model> QuerySnapshot.toObjectsWithId() =
    map { it.toObjectWithId<T>() }

fun DocumentSnapshot.toProfile() =
    toObjectWithId<Profile>()

fun getUser(id: String) = db
    .collection("users")
    .document(id)
    .get()
    .continueWith { it -> it.result!!.toProfile() }

fun getRoutes() = db
    .collection("routes")
    .get()
    .continueWith { it.result!!.toObjectsWithId<RouteItem>() }

fun getPoints(id: String) = db
    .collection("points")
    .document(id)
    .get()
    .continueWith {
        it.result!!.toObject(RoutePointsItem::class.java)!!.points
            .map { LatLng(it.latitude, it.longitude) }
    }

fun setPoints(points: List<LatLng>) = db
    .collection("points")
    .add(RoutePointsItem(points.map { GeoPoint(it.latitude, it.longitude) }))
