package net.chenxiy.bilimusic.view;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.adapter.DynamicFragmentPagerAdapter;
import net.chenxiy.bilimusic.viewmodel.DynamicViewModel;

public class DynamicFragment extends Fragment {

    private static final String TAG = "DynamicFragmentLog";
    private DynamicViewModel mViewModel;
    private ViewPager hotsongViewPager;
    private TabLayout dynamicTab;
    private PagerAdapter pagerAdapter;

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dynamic_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DynamicViewModel.class);
        // TODO: Use the ViewModel
        pagerAdapter=new DynamicFragmentPagerAdapter(getFragmentManager());

        hotsongViewPager=getView().findViewById(R.id.hotsong_viewpager);
        hotsongViewPager.setAdapter(pagerAdapter);
        dynamicTab=getView().findViewById(R.id.tablayout);
        hotsongViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(dynamicTab));
        dynamicTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: "+tab.getPosition());
                hotsongViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        hotsongViewPager.setOffscreenPageLimit(1);


    }

}
