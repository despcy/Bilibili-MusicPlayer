package net.chenxiy.biliwebview.interceptwebclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class InterceptWebClient extends WebViewClient {
    private static final String TAG = "InterceptWebClientLog";
    private ArrayList<String> mDomainWhiteList;
    private ArrayList<String> mDomainBlackList;

    private RequestNotifier notifier;

    private ArrayList<StringAppendRule> stringAppendRules;
    private ArrayList<FileReplaceRule> fileReplaceRules;

    public ArrayList<String> getJsExecWhenLoading() {
        return jsExecWhenLoading;
    }

    public void setJsExecWhenLoading(ArrayList<String> jsExecWhenLoading) {
        this.jsExecWhenLoading = jsExecWhenLoading;
    }

    private ArrayList<String> jsExecWhenLoading;


    public void setNotifier(RequestNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "onLoadResource: "+url);
        super.onLoadResource(view, url);
        String inDomain="";
        try {
            inDomain=Utils.getDomainName(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
        if(notifier!=null){
            notifier.onURLRequest(url);
        }


        for(String domain:mDomainBlackList){
            if(url.contains(domain)){
                Log.d(TAG, "onLoadResource: blackList"+url);
                return true;
            }
        }
        for(String domain:mDomainWhiteList){
            if(inDomain.contains(domain)){
                Log.d(TAG, "onLoadResource: whiteList"+url);
                return false;
            }
        }



        return false;//return false means load the resource


    }

    public InterceptWebClient() {
        mDomainWhiteList=new ArrayList<>();
        mDomainBlackList=new ArrayList<>();
        stringAppendRules=new ArrayList<>();
        fileReplaceRules=new ArrayList<>();
        jsExecWhenLoading=new ArrayList<>();
        notifier=null;
    }



    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.d(TAG, "shouldInterceptRequest: request:" + request.getUrl().toString());



        for (FileReplaceRule fileReplaceRule : fileReplaceRules) {
            if (fileReplaceRule.getRule().shouldReplacementHappen(request)) {
                //replace file
                String mimeType=new String();
                InputStream inputStream=httpRequest(request,mimeType);
                WebResourceResponse response= new WebResourceResponse(mimeType, "UTF-8", fileReplaceRule.getFile());
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }
        }
        for (StringAppendRule stringAppendRule : stringAppendRules) {
            if (stringAppendRule.getRule().shouldReplacementHappen(request)) {
                //addstring
                String mimeType=new String();
                InputStream inputStream=httpRequest(request,mimeType);
                WebResourceResponse response= new WebResourceResponse(mimeType, "UTF-8", Utils.appendNewString(inputStream, stringAppendRule.getNewString()));


                return response;

            }
        }
        return null;
    }


    private InputStream httpRequest(WebResourceRequest request,String mimeType){
        HttpURLConnection conn = null;
        InputStream inputStream;
        try {
            URL requestUrl = new URL(request.getUrl().toString());

            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(conn.getInputStream());
                mimeType=conn.getContentType();
                return inputStream;
            } else {
                conn.disconnect();
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, "shouldInterceptRequest: Error", e);

            return null;
        }
    }


   public interface RequestNotifier{
        void onURLRequest(String url);
    }





    @Override
    public void onPageCommitVisible(WebView view, String url) {


        for(String js:jsExecWhenLoading){
            view.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }

        //super.onPageCommitVisible(view, url);

    }



    //    public void setStringReplaceRule(InterceptRule rule,String oldString,String newString){
//
//    }

    public void addStringAppendRule(InterceptRule rule,String newString){
        stringAppendRules.add(new StringAppendRule(rule,newString));
    }

   // public void injectJsBridge()

    public void addFileReplaceRule(InterceptRule rule, InputStream file){
        fileReplaceRules.add(new FileReplaceRule(rule,file));
    }

    public ArrayList<String> getmDomainWhiteList() {
        return mDomainWhiteList;
    }

    public void setmDomainWhiteList(ArrayList<String> mDomainWhiteList) {
        this.mDomainWhiteList = mDomainWhiteList;
    }

    public ArrayList<String> getmDomainBlackList() {
        return mDomainBlackList;
    }

    public void setmDomainBlackList(ArrayList<String> mDomainBlackList) {
        this.mDomainBlackList = mDomainBlackList;
    }
}
