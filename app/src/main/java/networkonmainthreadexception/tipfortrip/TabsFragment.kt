package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tabs.*


class TabsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TabsBuilder(viewpager, tabs)
            .add(EventsFragment(), "Маршруты")
            .add(MapFragment(), "Карта")
            .add(ProfileFragment(), "Профиль")
            .build(childFragmentManager)
        Plus.setOnClickListener {

        }
    }

}
