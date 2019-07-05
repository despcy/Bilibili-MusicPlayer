package net.chenxiy.bilimusic.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Update;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.databinding.ActivityPlayingBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_music.CodeResponse;
import net.chenxiy.bilimusic.service.MediaService;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import java.util.ArrayList;


public class PlayingActivity extends SuperActivity {

    ActivityPlayingBinding mBinding;

    public static String EXTRA_IMAGE_URL="url";
    public static String EXTRA_TITLE="title";
    public static String EXTRA_ARTIST="artist";
    public static String EXTRA_AVID="avid";


    private MainActivityViewModel mainActivityViewModel;
    private static final String TAG = "PlayingActivityLog";
    private boolean updateSeekBarLock=false;

    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playing);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.d(TAG, "onCreate: ");
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_playing);
        this.setSupportActionBar(mBinding.playingPageToolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        UpdateUI(mainActivityViewModel.nowPlayingAvData.getValue());

        mainActivityViewModel.nowPlayingAvData.observe(this, new Observer<AvData>() {
            @Override
            public void onChanged(AvData avData) {

                UpdateUI(avData);

            }
        });

        mBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                updateSeekBarLock=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mainActivityViewModel.appMediaBrowserHelper.
                        getController().
                        getTransportControls().seekTo(seekBar.getProgress());
                updateSeekBarLock=false;
            }
        });

        //Change the Current Play State
        mainActivityViewModel.isPlaying.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isplayingnow) {
                updatePlayPauseButton(isplayingnow);
            }
        });

        mBinding.btnPlaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: ");
                if(mainActivityViewModel.isPlaying.getValue()!=null) {
                    boolean isPlaying = mainActivityViewModel.isPlaying.getValue();

                    if(isPlaying) {
                        mainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().pause();
                    }else {
                        mainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().play();

                    }


                }

            }
        });

        mBinding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().skipToPrevious();

            }
        });

        mBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().skipToNext();
            }
        });

        mBinding.btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPlayList bottomSheetPlayList=new BottomSheetPlayList();
                bottomSheetPlayList.show(getSupportFragmentManager(),bottomSheetPlayList.getTag());//
            }
        });

        if(MainActivityViewModel.maxProgress.getValue()!=null) {
            updateSeekBar(MainActivityViewModel.playProgress.getValue(),MainActivityViewModel.bufferPosition.getValue(), MainActivityViewModel.maxProgress.getValue());

        }

        MainActivityViewModel.playProgress.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                    updateSeekBar(aLong,MainActivityViewModel.bufferPosition.getValue(),MainActivityViewModel.maxProgress.getValue());

            }
        });



        if(MainActivityViewModel.repeatMode.getValue()!=null) {
            updateUIRepeatMode(MainActivityViewModel.repeatMode.getValue());
        }

        MainActivityViewModel.repeatMode.observe(this, new Observer<Integer>() {
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


        mBinding.btnRepeatMode.setOnClickListener(switchRepeatModeOnclickListener);


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


        }else if(repeatMode==MediaService.REPEAT_SINGLE){
            mBinding.btnRepeatMode.setImageResource(R.drawable.ic_repeat_one_black_24dp);


        }else if(repeatMode==MediaService.REPEAT_SHUFFLE){
            mBinding.btnRepeatMode.setImageResource(R.drawable.ic_shuffle_black_24dp);

        }
    }

    private void updateSeekBar(Long progress,Long bufferPosition,Long max){
        if(!updateSeekBarLock) {


            mBinding.seekBar.setMax(max.intValue());
            mBinding.seekBar.setProgress(progress.intValue());
            mBinding.seekBar.setSecondaryProgress(bufferPosition.intValue());
            int duration = mainActivityViewModel.nowPlayingAvData.getValue().getPages().get(0).getDuration();
            mBinding.setProgressTime((int) (duration * (progress.intValue() * 1.0 / max.intValue())));


        }
    }

    private void UpdateUI(AvData avData){
        if(avData!=null) {
            mBinding.setAvData(avData);

            mBinding.setDuration(avData.getPages().get(0).getDuration()-1);
            mBinding.executePendingBindings();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nowplaying_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.song_info:

                DisplaySongInfo();
                return true;
            case R.id.add_to_fav_folder:

                addTofavSongFolder();
                return true;
            case R.id.open_in_bili:

                OpenInBili();
                return true;
            case android.R.id.home:
                //deal with the <- arrow in the toolbar
                Log.d(TAG, "onContextItemSelected: Up");

                startActivity(new Intent(this,MainActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    private void OpenInBili(){
        try {
            String url = "https://www.bilibili.com/video/av".concat(mainActivityViewModel.nowPlayingAvData.getValue().getAid().toString());
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            MainActivityViewModel.appMediaBrowserHelper.getController().getTransportControls().pause();
        }catch (Exception e){
            Toast.makeText(this, R.string.noApp,Toast.LENGTH_SHORT);
        }

    }

    private void DisplaySongInfo(){
        AvData avData=mainActivityViewModel.nowPlayingAvData.getValue();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Log.d(TAG, "DisplaySongInfo: ");
        builder.setMessage(avData.getDesc());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addTofavSongFolder(){
        View v=mBinding.getRoot();
        DataUtils.folderSelection(v.getContext(), v, new DataUtils.FolderSelectionCallback() {
            @Override
            public void onFolderSelected(ArrayList<Integer> fids) {
                Integer[] counter=new Integer[1];
                counter[0]=0;
                for (Integer folderId:fids){


                    Repository.getInstance(v.getContext()).addSongToFolder(mainActivityViewModel.nowPlayingAvData.getValue().getAid(), folderId, new Callback<CodeResponse>() {
                        @Override
                        public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
                            counter[0]++;
                            if(response.body().getCode()==0){

                                if(counter[0].intValue()==fids.size()) {
                                    MainActivity.reFetch(v.getContext());
                                }

                            }else{
                                Toast.makeText(v.getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<CodeResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(),"NETWORK FAILURE",Toast.LENGTH_SHORT).show();
                            counter[0]++;
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void updatePlayPauseButton(boolean isplayingnow){
        if (isplayingnow) {
            mBinding.btnPlaypause.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        } else {

            mBinding.btnPlaypause.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
    }





}
