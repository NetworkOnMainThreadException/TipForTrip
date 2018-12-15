package networkonmainthreadexception.tipfortrip


import android.content.Context
import com.google.android.gms.tasks.OnFailureListener


class MyFailureListener(
    val context: Context?,
    vararg val failures: Pair<Class<out Exception>, String>
) : OnFailureListener {

    override fun onFailure(e: Exception) {
        failures.find { it.first.isInstance(e) }
            ?.let { context?.showToast(it.second) }
            ?: context?.showToast(e.javaClass.simpleName + ": " + e.message)
    }
}
