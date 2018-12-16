package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_event_list.*


class EventListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val routeId = arguments!!.getString("routeId")!!
        FirebaseFirestore.getInstance().collection("events")
            .whereEqualTo("routeId", routeId)
            .get()
            .continueWith { it.result!!.toObjectsWithId<EventItem>() }
            .addOnSuccessListener { events ->
                val recyclerView = recycler_event_fragment
                recyclerView.adapter = EventsAdapter(context, events.toMutableList(), fragmentManager)

                recyclerView.layoutManager = LinearLayoutManager(context)

                val decoration = DividerItemDecoration(context, 1)
                decoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.list_divider_2)!!)
                recyclerView.addItemDecoration(decoration)
            }
    }

}


fun newInstance(routeId: String): EventListFragment {
    val args = Bundle()
    args.putString("routeId", routeId)
    val fragment = EventListFragment()
    fragment.arguments = args
    return fragment
}

fun join(sep: String, list: List<String>) : String {
    return list.joinToString(sep)
}
