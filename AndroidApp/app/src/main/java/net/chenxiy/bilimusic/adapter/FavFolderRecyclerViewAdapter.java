package net.chenxiy.bilimusic.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.databinding.FavFolderItemBinding;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData;
import net.chenxiy.bilimusic.view.AlbumDetailActivity;
import net.chenxiy.bilimusic.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavFolderRecyclerViewAdapter extends RecyclerView.Adapter<FavFolderRecyclerViewAdapter.FavFolderViewHolder> {

    private static final String TAG = "FavFolderRecyclerViewAdapterLog";
    private List<FolderArchive> folderArchiveArrayList;

    public FavFolderRecyclerViewAdapter() {
        this.folderArchiveArrayList = new ArrayList<FolderArchive>();
    }

    public void setData(List<FolderArchive> newfolderArchiveArrayList){
        this.folderArchiveArrayList=newfolderArchiveArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavFolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        FavFolderItemBinding binding=FavFolderItemBinding.inflate(inflater,parent,false);
        return new FavFolderViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavFolderViewHolder holder, int position) {
                holder.bind(folderArchiveArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return folderArchiveArrayList.size();
    }

    public static class FavFolderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{ //view holder
        private FavFolderItemBinding favFolderItemBinding;

        public FavFolderViewHolder(@NonNull View itemView,FavFolderItemBinding binding) {
            super(itemView);
            itemView.setOnClickListener(this);
            favFolderItemBinding=binding;

        }

        public void bind(FolderArchive folderArchive){
            favFolderItemBinding.setFolderInfo(folderArchive);
            favFolderItemBinding.executePendingBindings();
            favFolderItemBinding.menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu=new PopupMenu(v.getContext(),v);
                    menu.getMenuInflater().inflate(R.menu.favfoldermenu,menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.d(TAG, "onMenuItemClick: "+item.getTitle());
                            switch (item.getItemId()){
//                                case R.id.hideFolder:
//
//                                    break;
                                case R.id.deleteFolder:

                                    CreateDeleteDialog(v,folderArchive.getFid());
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
            Log.d(TAG, "onClick: "+getAdapterPosition()+favFolderItemBinding.getFolderInfo().getName());
            Intent intent=new Intent(v.getContext(), AlbumDetailActivity.class);
            FolderArchive favFolder=favFolderItemBinding.getFolderInfo();
            intent.putExtra(AlbumDetailActivity.TYPE, Constants.TYPE_FAV_FOLDER);
            intent.putExtra(AlbumDetailActivity.CAPTION,favFolder.getName());
            intent.putExtra(AlbumDetailActivity.ID,favFolder.getFid());
            if(favFolder.getCover()!=null) {
                intent.putExtra(AlbumDetailActivity.HEADER_PIC, favFolder.getCover().get(0).getPic());
            }
            v.getContext().startActivity(intent);
        }


        public void CreateDeleteDialog(View v,Integer fid){
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog);
            builder.setTitle("云端收藏夹将一并删除")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Repository.getInstance(v.getContext()).deleteNewFavFolder(fid, new Callback<AddDelFolderResponse>() {
                                @Override
                                public void onResponse(Call<AddDelFolderResponse> call, Response<AddDelFolderResponse> response) {

                                    if(response.body().getCode()==0){
                                        Toast.makeText(v.getContext(), R.string.deleteFolderSuccess,Toast.LENGTH_SHORT).show();

                                        MainActivity.reFetch(v.getContext());
                                    }else{
                                        Toast.makeText(v.getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<AddDelFolderResponse> call, Throwable t) {
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
