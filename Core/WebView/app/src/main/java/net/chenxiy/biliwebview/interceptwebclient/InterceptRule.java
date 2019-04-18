package net.chenxiy.biliwebview.interceptwebclient;

import android.webkit.WebResourceRequest;

public interface InterceptRule {
    Boolean shouldReplacementHappen(WebResourceRequest request);
}
