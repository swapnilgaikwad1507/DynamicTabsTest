package vidyoconnector.vidyo.com.tablayouttutorial.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import vidyoconnector.vidyo.com.tablayouttutorial.fragments.DynamicFragment;

public class PlansPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return DynamicFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}