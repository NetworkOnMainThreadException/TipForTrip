package networkonmainthreadexception.tipfortrip

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.app.DatePickerDialog
import java.util.*


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

fun pickDateUntil(context: Context, listener: (Date) -> Unit, maxDate: Date) {
    val c = Calendar.getInstance() // default date
    val dialog = DatePickerDialog(
        context, 0,
        { _, y, m, d -> listener(GregorianCalendar(y, m, d).time) },
        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
    dialog.datePicker.maxDate = maxDate.getTime()
    dialog.show()
}

fun pickDateUntilNow(context: Context, listener: (Date) -> Unit) {
    pickDateUntil(context, listener, Calendar.getInstance().time)
}
