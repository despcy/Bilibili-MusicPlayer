package net.chenxiy.bilimusic.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.adapter.ArtistCardRecyclerViewAdapter;
import net.chenxiy.bilimusic.adapter.BottomPlayListRecyclerViewAdapter;
import net.chenxiy.bilimusic.adapter.FavFolderRecyclerViewAdapter;
import net.chenxiy.bilimusic.databinding.BottomSheetPlayListFragmentBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_music.CodeResponse;
import net.chenxiy.bilimusic.service.MediaService;
import net.chenxiy.bilimusic.viewmodel.BottomSheetPlayListViewModel;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class BottomSheetPlayList extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetPlayListLog";
    private BottomSheetPlayListViewModel mViewModel;
    private BottomPlayListRecyclerViewAdapter bottomPlayListRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private BottomSheetPlayListFragmentBinding mBinding;
    public static BottomSheetPlayList newInstance() {
        return new BottomSheetPlayList();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(inflater,R.layout.bottom_sheet_play_list_fragment, container, false);
         return mBinding.getRoot();
    }

    private  ArtistCardRecyclerViewAdapter artistCardRecyclerViewAdapter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BottomSheetPlayListViewModel.class);
        Log.d(TAG, "onActivityCreated: ");
        bottomPlayListRecyclerViewAdapter=new BottomPlayListRecyclerViewAdapter();
        mBinding.recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);//if use the mBinding.recyclerView directly, there will be error inflating layout
        mBinding.recyclerView.setAdapter(bottomPlayListRecyclerViewAdapter);


        MainActivityViewModel.nowPlayingAvData.observe(getViewLifecycleOwner(), new Observer<AvData>() {
            @Override
            public void onChanged(AvData avData) {
                bottomPlayListRecyclerViewAdapter.notifyDataSetChanged();
                scrollToNowPlaying();
            }
        });


        MainActivityViewModel.currentPlayList.observe(getViewLifecycleOwner(), new Observer<ArrayList<AvData>>() {
            @Override
            public void onChanged(ArrayList<AvData> avData) {
                bottomPlayListRecyclerViewAdapter.notifyDataSetChanged();

            }
        });

        if(MainActivityViewModel.repeatMode.getValue()!=null) {
            updateUIRepeatMode(MainActivityViewModel.repeatMode.getValue());
        }
        MainActivityViewModel.repeatMode.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer repeatMode) {

                updateUIRepeatMode(repeatMode);

            }
        });


        View.OnClickListener switchRepeatModeOnclickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchRepeatMode();
            }
        };

        scrollToNowPlaying();

        mBinding.txtRepeatMode.setOnClickListener(switchRepeatModeOnclickListener);
        mBinding.btnRepeatMode.setOnClickListener(switchRepeatModeOnclickListener);
        mBinding.addtoFavfolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             AddAllToFolder();
            }
        });

    }

    private void AddAllToFolder(){
        DataUtils.folderSelection(getContext(), getView(), new DataUtils.FolderSelectionCallback() {
            @Override
            public void onFolderSelected(ArrayList<Integer> fids) {
                ArrayList<AvData> avDataList=MainActivityViewModel.currentPlayList.getValue();
                Integer[] count=new Integer[1];
                count[0]=0;
                for (Integer fid:fids){
                    for(AvData avdata:avDataList){
                        Repository.getInstance(getContext()).addSongToFolder(avdata.getAid(), fid, new Callback<CodeResponse>() {
                            @Override
                            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                                count[0]++;
                                if(response.body().getCode()==0) {
                                    if (count[0].intValue() == fids.size() * avDataList.size()) {
                                        Toast.makeText(getContext(), R.string.allAddSuccess, Toast.LENGTH_SHORT).show();
                                        MainActivity.reFetch(getContext());
                                    }
                                }else{
                                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CodeResponse> call, Throwable t) {
                                Toast.makeText(getContext(),"NETWORK FAILURE",Toast.LENGTH_SHORT).show();

                                count[0]++;

                            }
                        });
                    }
                }
            }
        });
    }



    private void scrollToNowPlaying(){
        try {


            AvData nowPlayingData = MainActivityViewModel.nowPlayingAvData.getValue();
            ArrayList<AvData> avData = MainActivityViewModel.currentPlayList.getValue();
            if (avData != null && nowPlayingData != null) {
                Integer index = -1;
                for (int i = 0; i < avData.size(); i++) {
                    if (avData.get(i) != null && avData.get(i).getAid().equals(nowPlayingData.getAid())) {
                        index = i;
                        index = avData.size() - 1 - index;
                        break;
                    }
                }


                Log.d(TAG, "onScrollTo: " + index);
                mBinding.recyclerView.getLayoutManager().scrollToPosition(index);
            }
        }catch (Exception e){

        }
    }

    void switchRepeatMode(){
        Integer repeatMode=MainActivityViewModel.repeatMode.getValue();
        if(repeatMode!=null&&MainActivityViewModel.appMediaBrowserHelper.getController()!=null) {

            repeatMode=(repeatMode+1)%3;

            MainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().setRepeatMode(repeatMode);
            Log.d(TAG, "switchRepeatMode: "+repeatMode);

        }
    }

    void updateUIRepeatMode(Integer repeatMode){
        Log.d(TAG, "updateUIRepeatMode: "+repeatMode);
        if(repeatMode== MediaService.REPEAT_ALL){
            mBinding.btnRepeatMode.setImageResource(R.drawable.ic_repeat_black_24dp);
            mBinding.txtRepeatMode.setText(R.string.repeat_all);

        }else if(repeatMode==MediaService.REPEAT_SINGLE){
            mBinding.btnRepeatMode.setImageResource(R.drawable.ic_repeat_one_black_24dp);
            mBinding.txtRepeatMode.setText(R.string.repeat_single);


        }else if(repeatMode==MediaService.REPEAT_SHUFFLE){
            mBinding.btnRepeatMode.setImageResource(R.drawable.ic_shuffle_black_24dp);
            mBinding.txtRepeatMode.setText(R.string.repeat_shuffle);

        }
    }

}
