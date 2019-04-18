package net.chenxiy.bilimusic.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.databinding.ArtistItemCardviewBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.Owner;
import net.chenxiy.bilimusic.view.AlbumDetailActivity;
;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistCardRecyclerViewAdapter extends PagedListAdapter<FavSong, ArtistCardRecyclerViewAdapter.ArtistCardViewHolder> {
    private static final String TAG = "ArtistCardRecyclerViewAdapterLog";

    public ArtistCardRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public ArtistCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ArtistItemCardviewBinding binding=ArtistItemCardviewBinding.inflate(inflater,parent,false);
        return new ArtistCardViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistCardViewHolder holder, int position) {
            holder.bind(getItem(position));
    }


    public static DiffUtil.ItemCallback<FavSong> DIFF_CALLBACK=new DiffUtil.ItemCallback<FavSong>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavSong oldItem, @NonNull FavSong newItem) {
            return oldItem.getOwner().getMid()==newItem.getOwner().getMid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavSong oldItem, @NonNull FavSong newItem) {
            return oldItem.equals(newItem);
        }
    };
    public static class ArtistCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ArtistItemCardviewBinding artistItemCardviewBinding;

        public ArtistCardViewHolder(@NonNull View itemView,ArtistItemCardviewBinding binding) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistItemCardviewBinding=binding;
        }

        public void bind(FavSong artistData){

            artistItemCardviewBinding.setFavSong(artistData);
            artistItemCardviewBinding.executePendingBindings();
        }


        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: "+getAdapterPosition()+artistItemCardviewBinding.getFavSong().getOwner().getName());
            Owner mOwner=artistItemCardviewBinding.getFavSong().getOwner();
            Intent intent=new Intent(v.getContext(), AlbumDetailActivity.class);
            intent.putExtra(AlbumDetailActivity.TYPE, Constants.TYPE_ARTIST_FOLDER);
            intent.putExtra(AlbumDetailActivity.CAPTION,mOwner.getName());
            intent.putExtra(AlbumDetailActivity.ID,mOwner.getMid());
            intent.putExtra(AlbumDetailActivity.HEADER_PIC,mOwner.getFace());
            v.getContext().startActivity(intent);
        }
    }
}
