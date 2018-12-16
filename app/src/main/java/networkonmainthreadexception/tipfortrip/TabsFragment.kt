package networkonmainthreadexception.tipfortrip


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tabs.*
import androidx.viewpager.widget.ViewPager


class TabsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager.adapter = ViewPagerAdapter(
            childFragmentManager,
            "Маршруты" to RouteListFragment(),
            "Карта" to MapFragment(),
            "Профиль" to ProfileFragment()
        )

        bottomNavigation.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when (it.itemId) {
                R.id.events -> 0
                R.id.map -> 1
                R.id.profile -> 2
                else -> -1
            }
            false
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var prevMenuItem: MenuItem? = null

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                (prevMenuItem ?: bottomNavigation.menu.getItem(0)).isChecked = false
                bottomNavigation.menu.getItem(position).isChecked = true
                prevMenuItem = bottomNavigation.menu.getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        Plus.setOnClickListener {
            pushFragment(fragmentManager!!, CreateNewRouteFragment());
        }
    }

}
