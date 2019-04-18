package net.chenxiy.bilimusic.network.interceptwebclient;


public class StringReplaceRule {
    private InterceptRule rule;
    private String oldString;
    private String newString;

    public InterceptRule getRule() {
        return rule;
    }

    public void setRule(InterceptRule rule) {
        this.rule = rule;
    }

    public String getOldString() {
        return oldString;
    }

    public void setOldString(String oldString) {
        this.oldString = oldString;
    }

    public String getNewString() {
        return newString;
    }

    public void setNewString(String newString) {
        this.newString = newString;
    }
}
