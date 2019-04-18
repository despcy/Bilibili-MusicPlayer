package net.chenxiy.biliwebview.interceptwebclient;

public class StringAppendRule {
    InterceptRule rule;
    String newString;

    public StringAppendRule(InterceptRule rule, String newString) {
        this.rule = rule;
        this.newString = newString;
    }

    public InterceptRule getRule() {
        return rule;
    }

    public void setRule(InterceptRule rule) {
        this.rule = rule;
    }

    public String getNewString() {
        return newString;
    }

    public void setNewString(String newString) {
        this.newString = newString;
    }


}
