package net.chenxiy.bilimusic.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.view.DynamicFragment;
import net.chenxiy.bilimusic.view.FavFolderFragment;
import net.chenxiy.bilimusic.view.WebViewFragment;

public class MainFragmentPageAdapter extends FragmentPagerAdapter {
    public MainFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==Constants.SONG_LIST_INDEX){
            return new FavFolderFragment();
        }else if(i==Constants.WEB_VIEW_INDEX){
            return new WebViewFragment();
        }else if(i== Constants.DYNAMIC_VIEW_INDEX){
            return new DynamicFragment();
        }

        return null;

    }

    @Override
    public int getCount() {
        return Constants.NUM_PAGES;
    }



}
