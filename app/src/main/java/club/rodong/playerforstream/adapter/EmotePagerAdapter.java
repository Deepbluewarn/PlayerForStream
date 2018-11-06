package club.rodong.playerforstream.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import club.rodong.playerforstream.Fragment.EmoteFragment;

public class EmotePagerAdapter extends FragmentPagerAdapter {
    private int count;
    public EmotePagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("EmotePagerAdapter", "getItem: page : " + position);
        return EmoteFragment.newInstance(position,"테스트");
    }

    @Override
    public int getCount() {
        return count;
    }
    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
