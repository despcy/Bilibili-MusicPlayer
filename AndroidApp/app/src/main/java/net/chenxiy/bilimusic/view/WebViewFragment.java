package net.chenxiy.bilimusic.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelperCallback;
import net.chenxiy.bilimusic.network.interceptwebclient.InterceptWebClient;
import net.chenxiy.bilimusic.viewmodel.WebViewViewModel;

import java.io.IOException;

public class WebViewFragment extends Fragment {
    private static final String TAG = "WebViewFragmentLog";
    private WebViewViewModel mViewModel;
    private WebView mWebView;
    boolean isControllerConnected=false;
    private SwipeRefreshLayout mSwipeRefreshlayout;
    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_view_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WebViewViewModel.class);

        mSwipeRefreshlayout=getView().findViewById(R.id.websearchSwipe);
        mSwipeRefreshlayout.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorTheme));

        mWebView=getView().findViewById(R.id.BiliSearchWebView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        InterceptWebClient minterceptClient=new InterceptWebClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshlayout.setRefreshing(false);
            }
        };
        minterceptClient.getmDomainBlackList().add("https://m.bilibili.com/video/");
        minterceptClient.getmDomainBlackList().add("https://m.bilibili.com/index.html");
        minterceptClient.getmDomainBlackList().add("bilibili:");
        minterceptClient.getmDomainBlackList().add("https://www.bilibili.com/p/");
        minterceptClient.getmDomainBlackList().add("https://bangumi.bilibili.com/");
        minterceptClient.getmDomainBlackList().add("https://www.bilibili.com/bangumi/");


//        try {
//            minterceptClient.addFileReplaceRule(new net.chenxiy.biliwebview.interceptwebclient.InterceptRule() {
//                @Override
//                public Boolean shouldReplacementHappen(WebResourceRequest request) {
//                    if(request.getUrl().toString().contains("search-82467044.css")){
//                        return true;
//                    }
//                    return false;
//                }
//            },getAssets().open("search.css"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        minterceptClient.setNotifier(new  InterceptWebClient.RequestNotifier() {
            @Override
            public void onURLRequest(String url) {
                if(url.contains("https://m.bilibili.com/video/")){
                    String avNum=url.replaceAll("\\D+","");//get the number in URL
                    Log.d(TAG, "onURLRequest: BiliVideo:"+avNum);

                      //  Toast.makeText(mWebView.getContext(), avNum, Toast.LENGTH_LONG).show();
                        mViewModel.getmMediaBrowserHelper().addPlayListQueueItem(Integer.valueOf(avNum));

                }
            }
        });

        mWebView.setWebViewClient(minterceptClient);

        mWebView.loadUrl("https://m.bilibili.com/search.html");
        mSwipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
