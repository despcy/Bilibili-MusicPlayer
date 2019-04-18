package net.chenxiy.bilimusic.adapter;


import android.net.Uri;
import android.text.Layout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import net.chenxiy.bilimusic.R;

import static com.bumptech.glide.request.RequestOptions.*;

import androidx.databinding.BindingAdapter;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BiliBindingAdapters {
    @BindingAdapter({"image_url"})
    public static void setImageUrl(ImageView imgView, String url){


            Integer defaultResource=R.drawable.album_empty;


            Glide.with(imgView.getContext()).load(url)
                    .placeholder(defaultResource).fitCenter()
                    .into(imgView);



    }
    @BindingAdapter({"avatar_url"})
    public static void setAvatarUrl(ImageView imgView, String url){
        if (url==null)return;
        Glide.with(imgView.getContext()).load(url).fitCenter()
                .into(imgView);
    }


    @BindingAdapter({"background_url"})
    public static void setBackgoundUrl(ImageView imgView, String url){
        Integer defaultResource=R.drawable.headerdefault;
        Glide.with(imgView.getContext()).load(url).fitCenter().placeholder(defaultResource).apply(bitmapTransform(new BlurTransformation(2,3)))
                .into(imgView);
    }


}
