package net.chenxiy.bilimusic;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Toast;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FavFolderInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.view.MainActivity;
import net.chenxiy.bilimusic.viewmodel.FavFolderFragmentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataUtils {

    private static final String TAG = "DataUtilsLog";
    public static MediaMetadataCompat AvData2MediaMetadataCompat(AvData avData,Context context,Integer quality){//初代Version先不考虑




        String url=Repository.getInstance(context).getDownloadUrl(avData.getAid(),avData.getCid(),quality);
        if (url==null){
            //url="http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/57/72/48167257/48167257-1-32.flv?e=ig8euxZM2rNcNbh3hbUVhoMznwNBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1552450974&gen=playurl&nbs=1&oi=3086324883&os=wcsu&platform=pc&trid=6dcd45d90d984599be9701d46904b591&uipk=5&upsig=f8b75202583a1bac4d7b1d075248f6b3";
            return null;
        }


        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,avData.getAid().toString())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST,avData.getOwner().getName())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,avData.getPic())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE,avData.getTitle())
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, quality)
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI,url).build();
    }


    public static Integer getUserIDFromCookie(){
        if(CookieManager.getInstance().hasCookies()==false){
            return -1;
        }
        if(CookieManager.getInstance().getCookie("bilibili.com")==null){
            return -1;
        }
        String cookies= CookieManager.getInstance().getCookie("bilibili.com");
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains("DedeUserID")){
                String[] temp1=ar1.split("=");
                return Integer.valueOf(temp1[1]);

            }
        }
        return -1;


    }
    public static String getCookie(){
        if(CookieManager.getInstance().getCookie("bilibili.com")==null){
            return "";
        }
        return CookieManager.getInstance().getCookie("bilibili.com");
    }

    public static String getCSRFTokenFromCookie(){
        if(CookieManager.getInstance().hasCookies()==false){
            return "";
        }
        if(CookieManager.getInstance().getCookie("bilibili.com")==null){
            return "";
        }
        String cookies= CookieManager.getInstance().getCookie("bilibili.com");
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains("bili_jct")){
                String[] temp1=ar1.split("=");
                return temp1[1];

            }
        }
        return "";
    }


    public static void folderSelection(Context context, View v,FolderSelectionCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog);
        ArrayList<FolderArchive> favFolders= FavFolderFragmentViewModel.favFolderArchives;
        if(favFolders!=null) {

            String[] arr = new String[favFolders.size()];
            for(int i=0 ; i< favFolders.size();i++){
                arr[i] = favFolders.get(i).getName();
                //getProductName or any suitable method
            }
            ArrayList<Integer> selectedFid=new ArrayList<>();

            builder.setTitle("选择要添加到的收藏夹")

                    .setMultiChoiceItems(arr, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                selectedFid.add(favFolders.get(which).getFid());
                            } else{
                                // Else, if the item is already in the array, remove it
                                selectedFid.remove(favFolders.get(which).getFid());
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           callback.onFolderSelected(selectedFid);

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }else{
            builder.setTitle(context.getString(R.string.loginFirst));
        }
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public interface FolderSelectionCallback{
        void onFolderSelected(ArrayList<Integer> fid);
    }

}

