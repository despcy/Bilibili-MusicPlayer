package net.chenxiy.bilimusic.adapter;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.databinding.AlbumListSongItemBinding;
import net.chenxiy.bilimusic.databinding.FavFolderItemBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_music.CodeResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.view.AlbumDetailActivity;
import net.chenxiy.bilimusic.view.MainActivity;
import net.chenxiy.bilimusic.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailSongListAdapter extends RecyclerView.Adapter<AlbumDetailSongListAdapter.AlbumDetailSongHolder> {
    private static final String TAG = "AlbumDetailSongListAdapterLog";
    private List<FavSong> favSongList;
    private MutableLiveData<FavSong> songSelected;
    public AlbumDetailSongListAdapter(MutableLiveData<FavSong> onClickSong) {
        this.favSongList = new ArrayList<>();
        songSelected=onClickSong;
    }

    public void setData(List<FavSong> songs){
        favSongList=songs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumDetailSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        AlbumListSongItemBinding binding=AlbumListSongItemBinding.inflate(inflater,parent,false);
        return new AlbumDetailSongHolder(binding.getRoot(),binding);
    }


    @Override
    public void onBindViewHolder(@NonNull AlbumDetailSongHolder holder, int position){
        holder.bind(favSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return favSongList.size();
    }

    public class AlbumDetailSongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AlbumListSongItemBinding albumListSongItemBinding;
        public AlbumDetailSongHolder(@NonNull View itemView,AlbumListSongItemBinding mBinding) {
            super(itemView);
            itemView.setOnClickListener(this);

            albumListSongItemBinding=mBinding;
        }

        public void bind(FavSong favSong){

            if(MainActivityViewModel.nowPlayingAvData.getValue()!=null&&
                    MainActivityViewModel.nowPlayingAvData.getValue().getAid().
                    equals(favSong.getAid())){


                albumListSongItemBinding.albumSongTitle.setTextColor(ContextCompat.getColor(albumListSongItemBinding.getRoot().getContext(), R.color.colorTheme));
                albumListSongItemBinding.albumSongDescription.setTextColor(ContextCompat.getColor(albumListSongItemBinding.getRoot().getContext(),R.color.colorTheme));
            }else{

                albumListSongItemBinding.albumSongTitle.setTextColor(ContextCompat.getColor(albumListSongItemBinding.getRoot().getContext(), R.color.colorTitle));
                albumListSongItemBinding.albumSongDescription.setTextColor(ContextCompat.getColor(albumListSongItemBinding.getRoot().getContext(),R.color.colorSubTitle));
            }

            albumListSongItemBinding.setFavSong(favSong);
            albumListSongItemBinding.index.setText(String.valueOf(getAdapterPosition()+1));
            albumListSongItemBinding.executePendingBindings();

            albumListSongItemBinding.songMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu=new PopupMenu(v.getContext(),v);
                    menu.getMenuInflater().inflate(R.menu.songmenu,menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.d(TAG, "onMenuItemClick: "+item.getTitle());
                            switch (item.getItemId()){
                                case R.id.deleteSong:

                                    CreateDeleteDialog(v,favSong.getFid(),favSong.getAid());
                                    break;
                                case R.id.addToFolder:

                                    DataUtils.folderSelection(v.getContext(), v, new DataUtils.FolderSelectionCallback() {
                                        @Override
                                        public void onFolderSelected(ArrayList<Integer> fids) {
                                            Integer[] counter=new Integer[1];
                                            counter[0]=0;
                                            for (Integer folderId:fids){


                                                Repository.getInstance(v.getContext()).addSongToFolder(favSong.getAid(), folderId, new Callback<CodeResponse>() {
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
                                    break;
                                default:
                                    break;


                            }
                            return true;
                        }
                    });
                    menu.show();
                }

            });
        }


        @Override
        public void onClick(View v) {
            songSelected.postValue(favSongList.get(getAdapterPosition()));
        }


        public void CreateDeleteDialog(View v,Integer fid,Integer avId){
            Log.d(TAG, "CreateDeleteDialog: fid:"+fid+"avid:"+avId);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog);
            builder.setTitle("云端歌曲将一并删除")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Repository.getInstance(v.getContext()).deleteSongFromFolder(avId, fid, new Callback<CodeResponse>() {
                                @Override
                                public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {

                                    if(response.body().getCode()==0){
                                        Toast.makeText(v.getContext(), R.string.deleteSongSuccess,Toast.LENGTH_SHORT).show();

                                        MainActivity.reFetch(v.getContext());
                                    }else{
                                        Toast.makeText(v.getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<CodeResponse> call, Throwable t) {
                                    Toast.makeText(v.getContext(),"NETWORK FAILURE",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();

        }
    }
}
