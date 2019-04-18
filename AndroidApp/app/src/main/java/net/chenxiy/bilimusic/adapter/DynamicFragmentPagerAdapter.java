package net.chenxiy.bilimusic.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.view.HotSongsContentFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class DynamicFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "DynamicFragmentPagerAdapterLog";
    public DynamicFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        HotSongsContentFragment hotSongsContentFragment=new HotSongsContentFragment();
        Bundle bundle=new Bundle();
        Log.d(TAG, "getItem: "+position);
        switch (position){


            case 0:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.ORIG);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 1:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.COVER);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 2:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.VOCAL);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 3:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.ELEC);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 4:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.PLAY);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 5:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.MV);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 6:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.LIVE);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
            case 7:
                bundle.putInt(HotSongsContentFragment.CATE_ID,Constants.MISC);
                hotSongsContentFragment.setArguments(bundle);
                return hotSongsContentFragment;
                default:
                    break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }
}
