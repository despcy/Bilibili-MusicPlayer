package net.chenxiy.bilimusic.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.databinding.HotSongItemBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.HotSong;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HotSongPageListAdapter extends PagedListAdapter<HotSong, HotSongPageListAdapter.HotSongViewHolder> {


    public HotSongPageListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<HotSong> DIFF_CALLBACK=new DiffUtil.ItemCallback<HotSong>() {
        @Override
        public boolean areItemsTheSame(@NonNull HotSong oldItem, @NonNull HotSong newItem) {
            return oldItem.getId()==newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull HotSong oldItem, @NonNull HotSong newItem) {
            return oldItem.equals(newItem);
        }
    };
    @NonNull
    @Override
    public HotSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotSongItemBinding mbinding=HotSongItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HotSongViewHolder(mbinding.getRoot(),mbinding);


    }

    @Override
    public void onBindViewHolder(@NonNull HotSongViewHolder holder, int position) {
           HotSong hotSong=getItem(position);
           holder.bind(hotSong);
    }

    public static class HotSongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private HotSongItemBinding hotSongItemBinding;
        public HotSongViewHolder(@NonNull View itemView,HotSongItemBinding mbinding) {
            super(itemView);

            hotSongItemBinding=mbinding;
            itemView.setOnClickListener(this);

        }
        public void bind(HotSong hotSong){

                hotSongItemBinding.setHotSong(hotSong);

        }

        @Override
        public void onClick(View v) {
            if(hotSongItemBinding.getHotSong()!=null) {
                Integer avId = hotSongItemBinding.getHotSong().getId();

                //  Toast.makeText(mWebView.getContext(), avNum, Toast.LENGTH_LONG).show();
                MainActivityViewModel.appMediaBrowserHelper.addPlayListQueueItem(avId);
            }


        }
    }
}
