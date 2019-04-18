package net.chenxiy.bilimusic.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.adapter.ArtistCardRecyclerViewAdapter;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.viewmodel.FavFolderFragmentViewModel;
import net.chenxiy.bilimusic.adapter.FavFolderRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavFolderFragment extends Fragment {
    private static final String TAG = "FavFolderFragmentLog";
    private FavFolderFragmentViewModel mViewModel;
    private RecyclerView favFolderRecyclerView;
    private RecyclerView.LayoutManager favFolderlayoutManager;
    private FavFolderRecyclerViewAdapter mFavFolderAdapter;
    private RecyclerView artistCardRecyclerView;
    private RecyclerView.LayoutManager artistlayoutManager;
    private ArtistCardRecyclerViewAdapter mArtistCardAdapter;
    private SwipeRefreshLayout mSwipeRefrishLayout;
    public static FavFolderFragment newInstance() {
        return new FavFolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fav_folder_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(FavFolderFragmentViewModel.class);
        favFolderRecyclerView=getActivity().findViewById(R.id.recycler_view_fav_folder);
        favFolderRecyclerView.setHasFixedSize(true);//fav folder change content do not change the layout size
        favFolderlayoutManager=new LinearLayoutManager(getContext());
        favFolderRecyclerView.setLayoutManager(favFolderlayoutManager);
        //-------------------
        artistCardRecyclerView=getActivity().findViewById(R.id.recycler_view_artist);
        artistCardRecyclerView.setHasFixedSize(true);
        artistlayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        artistCardRecyclerView.setLayoutManager(artistlayoutManager);
        mFavFolderAdapter=new FavFolderRecyclerViewAdapter();
        favFolderRecyclerView.setAdapter(mFavFolderAdapter);
        mArtistCardAdapter=new ArtistCardRecyclerViewAdapter();
        artistCardRecyclerView.setAdapter(mArtistCardAdapter);
        //--------
        mSwipeRefrishLayout=getActivity().findViewById(R.id.fav_folder_refreshlayout);
        //------
        mSwipeRefrishLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorTheme));





        mSwipeRefrishLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               refreshData();
            }
        });





        mViewModel.getArtistList().observe(this, new Observer<PagedList<FavSong>>() {
            @Override
            public void onChanged(PagedList<FavSong> favSongs) {

                    mArtistCardAdapter.submitList(favSongs);

            }
        });


        LiveData<List<FolderArchive>> data=mViewModel.getFavFolderInfo(); //observe the fav folder list
        Observer<List<FolderArchive>> observer=new Observer<List<FolderArchive>>() {
            @Override
            public void onChanged(List<FolderArchive> folderArchives) {
                mFavFolderAdapter.setData(folderArchives);
                FavFolderFragmentViewModel.favFolderArchives=new ArrayList<>(folderArchives);
            }
        };

        data.observe(this,observer);







    }

    public void refreshData(){

        if(DataUtils.getCSRFTokenFromCookie().isEmpty()){//not login
            Toast.makeText(getContext(), R.string.loginBeforeSync,Toast.LENGTH_LONG).show();
            mSwipeRefrishLayout.setRefreshing(false);
            return;
        }
        mSwipeRefrishLayout.setRefreshing(true);

        mViewModel.refreshFavFolderInfoData()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String message) {
                        mSwipeRefrishLayout.setRefreshing(false);
                    }
                });
    }

}
