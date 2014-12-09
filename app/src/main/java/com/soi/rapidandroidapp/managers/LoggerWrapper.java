package com.soi.rapidandroidapp.managers;

import android.util.Log;

import com.soi.rapidandroidapp.BaseApplication;

import javax.inject.Inject;

public class LoggerWrapper {

    @Inject
    protected EnvironmentManager environmentManager;

    private String _tag = BaseApplication.APP_NAME;
    private static LoggerWrapper _instance ;
    public int LOGGER_LEVEL = environmentManager.getEnvironmentLogLevel();

    private LoggerWrapper() {
    }

    /**
     * Singleton
     */
    public synchronized static void getInstance(){
        if (_instance == null){
            _instance = new LoggerWrapper();
        }
    }

    /**
     * Logs with [INFO][tag] msg
     * @param tag The tag which the logger will shown in logcat
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logInfo(String tag, String msgFormat, Object...args) {
        Log.i(tag, String.format(msgFormat,args));
    }
    /**
     * Logs with [INFO][_tag] msg
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logInfo(String msgFormat, Object...args) {
        Log.i(_tag, String.format(msgFormat,args));
    }

    /**
     * Logs with [DEBUG][tag] msg
     * @param tag The tag which the logger will shown in logcat
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logDebug(String tag, String msgFormat, Object...args) {
        if(LOGGER_LEVEL <= Log.DEBUG)
            Log.d(tag, String.format(msgFormat,args));
    }
    /**
     * Logs with [DEBUG][_tag] msg
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logDebug(String msgFormat, Object...args) {
        if(LOGGER_LEVEL <= Log.WARN)
            Log.d(_tag,  String.format(msgFormat,args));
    }
    /**
     * Logs with [ERROR][tag] msg exception
     * @param tag The tag which the logger will shown in logcat
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logError(String tag, String msgFormat, Exception ex, Object...args) {
        Log.e(tag, String.format(msgFormat,args),ex);
    }
    /**
     * Logs with [ERROR][tag] msg exception
     * @param msgFormat the format the message will be appeared "Hello %s, isn't %s cool?
     * @param args the arguments which you want to use
     */
    public void logError(String msgFormat, Exception ex, Object...args) {
        Log.e(_tag, String.format(msgFormat,args), ex);
    }
}