package net.chenxiy.biliwebview;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.chenxiy.biliwebview.interceptwebclient.InterceptWebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private Button btn_logout;
    private Button btn_search;
    private Button btn_access;
    private TextView txt_cookie;
    private EditText txt_urllink;
    private static final String TAG = "MainActivity";
    public static final String WEB_TEST_URL="web_test_url";
    public static final String APP_CACAHE_DIRNAME = "/data/data/net.chenxiy.biliwebview/cache/webviewCache";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieManager.getInstance().setAcceptCookie(true);
        setContentView(R.layout.activity_main);
        btn_access=findViewById(R.id.access_button);
        btn_login=findViewById(R.id.login_button);
        btn_logout=findViewById(R.id.logout_button);
        btn_search=findViewById(R.id.search_button);
        txt_cookie=findViewById(R.id.textView);
        txt_urllink=findViewById(R.id.weburl_text);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),BiliLogin.class);
                startActivity(intent);
            }
        });
        txt_cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshCookieInfo();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                refreshCookieInfo();
            }
        });

        btn_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),WebViewTest.class);
                intent.putExtra(WEB_TEST_URL,txt_urllink.getText().toString());
                startActivity(intent);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),BiliSearch.class);
                startActivity(intent);
            }
        });

    }

    private void refreshCookieInfo(){
        CookieManager manager=CookieManager.getInstance();
        String CookieExists=String.valueOf(manager.hasCookies());
        String CookieContent=String.valueOf(manager.getCookie("bilibili.com"));
        txt_cookie.setText("CookieExists:\n"+CookieExists+"\n"+"CookieContent:\n"+CookieContent);
        Log.d(TAG, "refreshCookieInfo: CurrentCookie:"+CookieContent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCookieInfo();
    }
}
