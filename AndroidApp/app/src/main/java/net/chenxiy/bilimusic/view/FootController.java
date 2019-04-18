package net.chenxiy.bilimusic.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.databinding.FootPlaybarBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;


public class FootController extends Fragment {


    private static final String TAG = "FootControllerLog";
    private FootPlaybarBinding mBinding;
    
    public static FootController newInstance() {
        return new FootController();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding=DataBindingUtil.inflate(inflater, R.layout.foot_playbar, container, false);

        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Log.d(TAG, "onClick: Start PlayingActivity");
                        Intent intent=new Intent(v.getContext(),PlayingActivity.class);
                        startActivity(intent);

            }
        });

        mBinding.playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: ");
                if(MainActivityViewModel.isPlaying.getValue()!=null) {
                    boolean isPlaying = MainActivityViewModel.isPlaying.getValue();

                    if(isPlaying) {
                        MainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().pause();
                    }else {
                        MainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().play();

                    }


                }

            }
        });



        //swipe layout to switch the song
        //TODO: change this into the viewPager or solve the onClick conflict
//        mBinding.getRoot().setOnTouchListener(new OnSwipeTouchListener(getContext()) {
//            @Override
//            public void onSwipeLeft() {
//                mViewModel.getMediaBrowserHelper().getController().getTransportControls().skipToNext();
//            }
//
//            @Override
//            public void onSwipeRight() {
//                mViewModel.getMediaBrowserHelper().getController().getTransportControls().skipToPrevious();
//            }
//        });

        MainActivityViewModel.nowPlayingAvData.observe(getViewLifecycleOwner(), new Observer<AvData>() {
            @Override
            public void onChanged(AvData avData) {
                mBinding.setAvData(avData);
            }
        });


        //Change the Current Play State
        MainActivityViewModel.isPlaying.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isplayingnow) {
                    updatePlayPauseButton(isplayingnow);
            }
        });

        mBinding.btnPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPlayList bottomSheetPlayList=new BottomSheetPlayList();
                bottomSheetPlayList.show(getFragmentManager(),bottomSheetPlayList.getTag());//
            }
        });

        return mBinding.getRoot();
    }

    private void updatePlayPauseButton(boolean isplayingnow){
        if (isplayingnow) {
            mBinding.playPauseButton.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {

            mBinding.playPauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }





    @Override
    public void onStart() {
        super.onStart();


        if(MainActivityViewModel.isPlaying.getValue()!=null) {
            updatePlayPauseButton(MainActivityViewModel.isPlaying.getValue());
        }
        if(MainActivityViewModel.nowPlayingAvData.getValue()!=null) {
            mBinding.setAvData(MainActivityViewModel.nowPlayingAvData.getValue());
        }

        updateSeekBar(MainActivityViewModel.playProgress.getValue(),MainActivityViewModel.bufferPosition.getValue(), MainActivityViewModel.maxProgress.getValue());


        MainActivityViewModel.playProgress.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                updateSeekBar(aLong,MainActivityViewModel.bufferPosition.getValue(),MainActivityViewModel.maxProgress.getValue());

            }
        });




    }

    private void updateSeekBar(Long progress,Long bufferPosition,Long max){

if(progress!=null&&max!=null) {
    mBinding.songProgress.setMax(max.intValue());
    mBinding.songProgress.setProgress(progress.intValue());
    mBinding.songProgress.setSecondaryProgress(bufferPosition.intValue());
}




    }

    @Override
    public void onStop() {
        super.onStop();



    }






}
