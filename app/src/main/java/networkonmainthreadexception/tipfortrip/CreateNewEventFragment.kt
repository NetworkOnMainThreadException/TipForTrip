package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_new_event.*
import java.util.*


class CreateNewEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_new_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val routeId = arguments!!.getString("routeId")!!
        val date = Date(arguments!!.getLong("date"))
        buttonAddGood.setOnClickListener {
            val goodText = editTextNewItem.text.toString().trim()
            if (goodText.isEmpty()) return@setOnClickListener
            editTextNewItem.setText("")
            chipGroupGoods.addView(Chip(context).apply{
                text = goodText
                isClickable = true
                isCheckable = false
                setTextColor(resources.getColor(R.color.white))
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_color)
                isCloseIconEnabled = true
                setOnCloseIconClickListener { chipGroupGoods.removeView(this) }
            })
        }
        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val goods = chipGroupGoods.getChildren<Chip>().map { it.text.toString().trim() }
            val event = EventItem(title, emptyList(), goods, date, routeId)
            FirebaseFirestore.getInstance().collection("events")
                .add(event).addOnSuccessListener {
                    context?.showToast("Событие добавлено")
                    fragmentManager?.setFragment(TabsFragment())
                }
        }
    }

}

inline fun <reified T : View> ViewGroup.getChildren(): List<T> {
    val list = mutableListOf<T>()
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child is T) {
            list.add(child)
        }
    }
    return list
}

fun newInstance(routeId: String, date: Long): CreateNewEventFragment {
    val args = Bundle()
    args.putString("routeId", routeId)
    args.putLong("date", date)
    val fragment = CreateNewEventFragment()
    fragment.arguments = args
    return fragment
}
