package networkonmainthreadexception.tipfortrip

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun FragmentManager.setFragment(fragment: Fragment) {
    beginTransaction()
        .replace(R.id.root, fragment)
        .commit()
}

fun pushFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    fragmentManager
        .beginTransaction()
        .replace(R.id.root, fragment)
        .addToBackStack(null)
        .commit()
}
