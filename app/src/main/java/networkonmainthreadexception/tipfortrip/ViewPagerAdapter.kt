package networkonmainthreadexception.tipfortrip


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class ViewPagerAdapter(
    manager: FragmentManager?,
    private vararg val items: Pair<String, Fragment>
) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int) = items[position].second

    override fun getCount() = items.size

    override fun getPageTitle(position: Int) = items[position].first
}

