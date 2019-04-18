package net.chenxiy.bilimusic.network.interceptwebclient;

import java.io.InputStream;

public class FileReplaceRule {
    private InterceptRule rule;
    private InputStream file;

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public InterceptRule getRule() {
        return rule;
    }

    public void setRule(InterceptRule rule) {
        this.rule = rule;
    }


    public FileReplaceRule(InterceptRule rule, InputStream file) {
        this.rule = rule;
        this.file = file;
    }
}
