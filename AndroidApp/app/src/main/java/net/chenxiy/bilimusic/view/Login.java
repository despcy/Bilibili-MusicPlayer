package net.chenxiy.bilimusic.view;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import net.chenxiy.bilimusic.network.interceptwebclient.InterceptRule;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.network.interceptwebclient.InterceptWebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Login extends AppCompatActivity {
    private static final String TAG = "BiliLogin";
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<String> loginWebViewWhiteList =
            new ArrayList(Arrays.asList("bilibili.com", "biligame.com"));//biligame 好像没啥用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate: LOCAL"+ Locale.getDefault().getLanguage());

        mWebView = (WebView) findViewById(R.id.wv_login);

        mSwipeRefreshLayout=findViewById(R.id.loginRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorTheme));
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

        InterceptWebClient minterceptClient = new InterceptWebClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }

        };
        minterceptClient.setNotifier(new InterceptWebClient.RequestNotifier() {
            @Override
            public void onURLRequest(String url) {
                if(url.contains("https://passport.biligame.com/crossDomain")){
                    backToMainActivity();
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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    mWebView.reload();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }else{
           backToMainActivity();

        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);

    }

    private void backToMainActivity(){
        CookieManager.getInstance().flush();

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.SHOULD_REFRESH,true);
        finish();
        startActivity(intent);
    }



}
