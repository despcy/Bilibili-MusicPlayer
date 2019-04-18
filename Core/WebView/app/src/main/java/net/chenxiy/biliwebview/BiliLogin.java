package net.chenxiy.biliwebview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.chenxiy.biliwebview.interceptwebclient.InterceptRule;
import net.chenxiy.biliwebview.interceptwebclient.InterceptWebClient;
import net.chenxiy.biliwebview.interceptwebclient.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class BiliLogin extends AppCompatActivity {
    private static final String TAG = "BiliLogin";
    private WebView mWebView;
    private ArrayList<String> loginWebViewWhiteList =
            new ArrayList(Arrays.asList("bilibili.com", "biligame.com"));//biligame 好像没啥用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mWebView = (WebView) findViewById(R.id.wv_login);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        InterceptWebClient minterceptClient = new InterceptWebClient();
        minterceptClient.setNotifier(new InterceptWebClient.RequestNotifier() {
            @Override
            public void onURLRequest(String url) {
                if(url.equals("https://m.bilibili.com/index.html")){
                    finish();
                }
            }
        });
        minterceptClient.setmDomainWhiteList(loginWebViewWhiteList);

        String cssHide="\ndiv.sns-box{\n" +
                "    display:none\n" +
                "}";
        minterceptClient.addStringAppendRule(new InterceptRule() {
            @Override
            public Boolean shouldReplacementHappen(WebResourceRequest request) {
                if(request.getUrl().toString().contains(".css")){
                    return true;
                }
                return false;
            }
        },cssHide);
        mWebView.setWebViewClient(minterceptClient);
        mWebView.loadUrl("https://passport.bilibili.com/login");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }else{
            finish();
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);

    }

}
