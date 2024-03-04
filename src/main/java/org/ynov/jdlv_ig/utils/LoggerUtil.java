package org.ynov.jdlv_ig.utils;

import java.util.ResourceBundle;

public class LoggerUtil implements System.Logger {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isLoggable(Level level) {
        return false;
    }

    @Override
    public void log(Level level, String msg) {
        System.Logger.super.log(level, msg);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {

    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {

    }
}
