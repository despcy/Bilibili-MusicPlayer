package net.chenxiy.bilimusic.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.adapter.HotSongPageListAdapter;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.HotSong;
import net.chenxiy.bilimusic.viewmodel.HotSongViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HotSongsContentFragment extends Fragment {
    private static final String TAG = "HotSongsContentFragmentLog";
    public static final String CATE_ID="cateID";
    private Integer categoryId;
    private HotSongViewModel mViewModel;
    private RecyclerView hotsongRecyclerView;
    private SwipeRefreshLayout mSwipeRefrishLayout;
    private HotSongPageListAdapter adapter;
    public HotSongsContentFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_hot_songs,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hotsongRecyclerView=getView().findViewById(R.id.hotsongRecyclerView);
        adapter=new HotSongPageListAdapter();
        mViewModel = ViewModelProviders.of(this).get(HotSongViewModel.class);
        //TODO: request resources
        categoryId= getArguments().getInt(CATE_ID);
        mSwipeRefrishLayout=getView().findViewById(R.id.swipeRefreshLayout);
        hotsongRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        mSwipeRefrishLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorTheme));
        mViewModel.netLoadStatus.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mSwipeRefrishLayout.setRefreshing(false);
            }
        });

        mSwipeRefrishLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.invalidateData();
            }
        });
        mViewModel.getPagedListLiveData(categoryId).observe(getViewLifecycleOwner(), new Observer<PagedList<HotSong>>() {
            @Override
            public void onChanged(PagedList<HotSong> hotSongs) {

                    adapter.submitList(hotSongs);

            }
        });
        hotsongRecyclerView.setAdapter(adapter);
    }
}
