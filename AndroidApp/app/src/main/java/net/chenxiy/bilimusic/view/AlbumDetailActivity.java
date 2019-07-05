package net.chenxiy.bilimusic.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.adapter.AlbumDetailSongListAdapter;
import net.chenxiy.bilimusic.databinding.ActivityAlbumDetailBinding;
import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelperCallback;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.service.MediaService;
import net.chenxiy.bilimusic.viewmodel.AlbumDetailActicityViewModel;
import net.chenxiy.bilimusic.viewmodel.FavFolderFragmentViewModel;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AlbumDetailActivity extends SuperActivity{

    private static final String TAG = "AlbumDetailActivityLog";
    public static final String TYPE="type";
    public static final String ID="id";
    public static final String HEADER_PIC="picture";
    public static final String CAPTION="caption";
    private ActivityAlbumDetailBinding mBinding;
    private AlbumDetailActicityViewModel mViewModel;
    private MainActivityViewModel mainActivityViewModel;
    private AlbumDetailSongListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Boolean isControllerConnected=false;
    private ActionBar actionBar;
    MutableLiveData<FavSong> songSelected=new MutableLiveData<>();
    private List<FavSong> currentList;
    private Integer type;
    public static Integer id=0;
    private String headerPictureUrl;
    private String caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_album_detail);
        this.setSupportActionBar(mBinding.albumDetailtoolbar);
        actionBar=getSupportActionBar();
        mViewModel = ViewModelProviders.of(this).get(AlbumDetailActicityViewModel.class);
        mainActivityViewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);
        Intent intent=getIntent();
        type=intent.getIntExtra(TYPE,0);
        id=intent.getIntExtra(ID,0);
        headerPictureUrl=intent.getStringExtra(HEADER_PIC);
        caption=intent.getStringExtra(CAPTION);
        mLayoutManager=new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL,false);
        mAdapter=new AlbumDetailSongListAdapter(songSelected);
        mBinding.albumRecyclerView.setAdapter(mAdapter);
        mBinding.albumRecyclerView.setLayoutManager(mLayoutManager);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (type.equals(Constants.TYPE_FAV_FOLDER)){
            initializeFavFolder();
        }else if(type.equals(Constants.TYPE_ARTIST_FOLDER)){
            initializeArtistFolder();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);//set the status bar totally tranparent
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        songSelected.observe(this, new Observer<FavSong>() {
            @Override
            public void onChanged(FavSong favSong) {

                if(favSong!=null&&currentList!=null) {
                    ArrayList<Integer> songIDs=new ArrayList<>();
                    Integer cursor=-1;
                    for(int i=currentList.size()-1;i>=0;i--){
                        if(favSong.getAid().equals(currentList.get(i).getAid())){
                            cursor=currentList.size()-1-i;
                        }
                        songIDs.add(currentList.get(i).getAid());
                    }

                    Bundle bundle=new Bundle();
                    bundle.putIntegerArrayList(MediaService.EXTRA_PLAYLIST,songIDs);
                    bundle.putInt(MediaService.EXTRA_CURSOR,cursor);
                    mViewModel.getmMediaBrowserHelper().getController().getTransportControls().sendCustomAction(MediaService.COMMAND_SET_PLAYLIST,bundle);
                }

            }
        });

        MainActivityViewModel.nowPlayingAvData.observe(this, new Observer<AvData>() {
            @Override
            public void onChanged(AvData avData) {
                mAdapter.notifyDataSetChanged();
            }
        });





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void initializeFavFolder(){




        actionBar.setTitle(caption);

        loadHeaderImage();

        LiveData<List<FavSong>> favSongs=mViewModel.getFavSongList(id);
        favSongs.observe(this, new Observer<List<FavSong>>() {
            @Override
            public void onChanged(List<FavSong> favSongs) {
                currentList=favSongs;
                mAdapter.setData(favSongs);
            }
        });
    }

    private void initializeArtistFolder(){
        actionBar.setTitle(caption);

        loadHeaderImage();
        LiveData<List<FavSong>> favSongs=mViewModel.getArtistSongs(id);
        favSongs.observe(this, new Observer<List<FavSong>>() {
            @Override
            public void onChanged(List<FavSong> favSongs) {
                currentList=favSongs;
                mAdapter.setData(favSongs);
            }
        });

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {//copied form stackOverFlow to make the system status bar translucent
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void loadHeaderImage(){
        Integer defaultResource=R.drawable.album_empty;
        Glide.with(getBaseContext()).load(headerPictureUrl)
                .placeholder(defaultResource).fitCenter()
                .into(mBinding.appBarImage);
    }


}
