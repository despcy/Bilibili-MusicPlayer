package net.chenxiy.biliwebview;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import net.chenxiy.biliwebview.interceptwebclient.InterceptRule;
import net.chenxiy.biliwebview.interceptwebclient.InterceptWebClient;

import java.io.IOException;
import java.io.InputStream;

public class BiliSearch extends AppCompatActivity {
    private static final String TAG = "BiliSearchLog";
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bili_search);

        mWebView=findViewById(R.id.testWebView);

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

        InterceptWebClient minterceptClient=new InterceptWebClient();
        minterceptClient.getmDomainBlackList().add("https://m.bilibili.com/video/");
        minterceptClient.getmDomainBlackList().add("https://m.bilibili.com/index.html");
        minterceptClient.getmDomainBlackList().add("bilibili:");
        minterceptClient.getmDomainBlackList().add("https://www.bilibili.com/p/");
        minterceptClient.getmDomainBlackList().add("https://bangumi.bilibili.com/");
        minterceptClient.getmDomainBlackList().add("https://www.bilibili.com/bangumi/");


        try {
            minterceptClient.addFileReplaceRule(new InterceptRule() {
                @Override
                public Boolean shouldReplacementHappen(WebResourceRequest request) {
                    if(request.getUrl().toString().contains("search-82467044.css")){
                        return true;
                    }
                    return false;
                }
            },getAssets().open("search.css"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        minterceptClient.setNotifier(new InterceptWebClient.RequestNotifier() {
            @Override
            public void onURLRequest(String url) {
                if(url.contains("https://m.bilibili.com/video/")){
                    String avNum=url.replaceAll("\\D+","");//get the number in URL
                    Log.d(TAG, "onURLRequest: BiliVideo:"+avNum);
                    Toast.makeText(mWebView.getContext(), avNum, Toast.LENGTH_LONG).show();
                }
            }
        });

        mWebView.setWebViewClient(minterceptClient);

        mWebView.loadUrl("https://m.bilibili.com/search.html");

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }else{
            //finish();
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);

    }
}
