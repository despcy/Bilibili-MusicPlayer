package net.chenxiy.bilimusic.network.interceptwebclient;

import android.webkit.WebResourceRequest;

public interface InterceptRule {
    Boolean shouldReplacementHappen(WebResourceRequest request);
}
