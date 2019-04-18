package net.chenxiy.bilimusic.adapter;

import android.content.Context;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.databinding.BottomMenuBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.view.MainActivity;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BottomPlayListRecyclerViewAdapter extends RecyclerView.Adapter<BottomPlayListRecyclerViewAdapter.BottomPlayListViewHolder>  {


    private static final String TAG = "BottomPlayListRecyclerViewAdapterLog";
    @NonNull
    @Override
    public BottomPlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View itemView=LayoutInflater.from(parent.getContext()).inflate( R.layout.playlist_bottommenu_item,parent,false);
        return new BottomPlayListViewHolder(itemView,parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull BottomPlayListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        //the position stored in the list is in reverse order
        int size=MainActivityViewModel.currentPlayList.getValue().size();
        holder.onBind(MainActivityViewModel.currentPlayList.getValue().get(size-1-position));
    }

    @Override
    public int getItemCount() {
        if(MainActivityViewModel.currentPlayList.getValue()!=null){
            return MainActivityViewModel.currentPlayList.getValue().size();
        }else {
            return 0;
        }
    }

    public class BottomPlayListViewHolder extends RecyclerView.ViewHolder{
        private BottomMenuBinding mBinding;
        private Context context;
        public BottomPlayListViewHolder(@NonNull View itemView,Context parentContext) {
            super(itemView);
            mBinding=DataBindingUtil.bind(itemView.getRootView());
            context=parentContext;


        }

        public void onBind(AvData avData){
            //TODO: add now playing

            mBinding.setAvData(avData);
            if(avData==null){
                return;
            }
            if(MainActivityViewModel.nowPlayingAvData.getValue().getAid().
                    equals(avData.getAid())){

                 mBinding.icIsnowplaying.setVisibility(View.VISIBLE);
                 mBinding.songtitle.setTextColor(ContextCompat.getColor(context,R.color.colorTheme));
                 mBinding.artist.setTextColor(ContextCompat.getColor(context,R.color.colorTheme));
            }else{
                mBinding.icIsnowplaying.setVisibility(View.GONE);
                mBinding.songtitle.setTextColor(ContextCompat.getColor(context,R.color.colorTitle));
                mBinding.artist.setTextColor(ContextCompat.getColor(context,R.color.colorSubTitle));
            }

            mBinding.executePendingBindings();

            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivityViewModel.appMediaBrowserHelper.getController()
                            .getTransportControls().skipToQueueItem(avData.getAid());
                }
            });

            mBinding.deleteSongInPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivityViewModel.appMediaBrowserHelper.getController()
                            .removeQueueItem(new MediaDescriptionCompat.Builder()
                                    .setMediaId(avData.getAid().toString())
                                    .build());
                }
            });
        }
    }
}
