package networkonmainthreadexception.tipfortrip;


import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class TabsBuilder {

    private final ViewPager viewPager;
    private final TabLayout tabLayout;

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public TabsBuilder(ViewPager viewPager, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }

    public TabsBuilder add(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
        return this;
    }

    public void build(FragmentManager fragmentManager, boolean keepAllTabs) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, fragments, titles);
        viewPager.setAdapter(adapter);
        if (keepAllTabs) {
            viewPager.setOffscreenPageLimit(fragments.size() - 1);
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    public void build(FragmentManager fragmentManager) {
        build(fragmentManager, false);
    }

}

