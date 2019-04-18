package net.chenxiy.biliwebview.interceptwebclient;

import android.webkit.WebResourceResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static InputStream appendNewString(InputStream stream,String str){

        InputStream addStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        InputStream newStream=new SequenceInputStream(stream,addStream);
        return newStream;
    }


    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public static void main(String[] args){
        try {
            System.out.println(getDomainName("https://passport.bilibili.com/web/captcha/combine?plat=1"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
