package networkonmainthreadexception.tipfortrip

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun pushFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    fragmentManager
        .beginTransaction()
        .replace(R.id.root, fragment)
        .addToBackStack(null)
        .commit()
}