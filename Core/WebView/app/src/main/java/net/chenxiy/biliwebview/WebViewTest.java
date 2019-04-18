package net.chenxiy.biliwebview;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.chenxiy.biliwebview.interceptwebclient.InterceptRule;
import net.chenxiy.biliwebview.interceptwebclient.InterceptWebClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static net.chenxiy.biliwebview.MainActivity.APP_CACAHE_DIRNAME;

public class WebViewTest extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
      mWebView=findViewById(R.id.testWebView);
      Intent intent=getIntent();
      String urlToLoad=intent.getStringExtra(MainActivity.WEB_TEST_URL);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCachePath(APP_CACAHE_DIRNAME);



        InputStream file = null;
        WebViewClient minterceptClient = new WebViewClient();
        try {
            file=getAssets().open("github.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

//            minterceptClient.addFileReplaceRule(new InterceptRule() {
//                @Override
//                public Boolean shouldReplacementHappen(WebResourceRequest request,String mimeType) {
//                    if(request.getUrl().toString().equals("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_160x56dp.png")){
//                        return true;
//                    }
//                    return false;
//                }
//            }, file);
//
//
//        String hideAppDLButton="";
//        minterceptClient.addStringAppendRule(new InterceptRule() {
//            @Override
//            public Boolean shouldReplacementHappen(WebResourceRequest request,String mimeType) {
//                if(mimeType.contains("text/html")){
//                    return true;
//                }
//                return false;
//            }
//        },hideAppDLButton);//hide the app download icon
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.setWebViewClient(minterceptClient);


        mWebView.loadUrl(urlToLoad);




    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            mWebView.clearCache(true);
            return true;
        }else{
            mWebView.clearCache(true);
            finish();
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);

    }

}
