package networkonmainthreadexception.tipfortrip

import com.google.firebase.firestore.Exclude

open class Model(
    @get:Exclude
    var id: String = ""
)
