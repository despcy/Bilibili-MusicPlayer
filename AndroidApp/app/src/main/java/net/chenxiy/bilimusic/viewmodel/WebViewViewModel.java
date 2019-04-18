package net.chenxiy.bilimusic.viewmodel;

import android.app.Application;

import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelper;
import net.chenxiy.bilimusic.service.MediaService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class WebViewViewModel extends AndroidViewModel {
    private MediaBrowserHelper mMediaBrowserHelper;

    public WebViewViewModel(@NonNull Application application) {
        super(application);
        mMediaBrowserHelper=MainActivityViewModel.appMediaBrowserHelper;

    }

    public MediaBrowserHelper getmMediaBrowserHelper() {
        return mMediaBrowserHelper;
    }
}
