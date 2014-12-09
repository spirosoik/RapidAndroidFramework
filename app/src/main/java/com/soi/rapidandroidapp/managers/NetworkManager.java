package com.soi.rapidandroidapp.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check device's network connectivity and speed
 */
public class NetworkManager {

    /**
     * Current context where is used
     */
    private Context context;

    /**
     * A static instance of the class for the Singlettion
     */
    private static NetworkManager _instance;

    /**
     * Singletton Pattern
     * @param ctx
     * @return
     */
    public synchronized static NetworkManager getInstance(Context ctx)
    {
        if (_instance == null) {
            _instance = new NetworkManager(ctx);
        }
        return _instance;
    }

    private NetworkManager(Context context) {
        this.context = context;
    }

    /**
     * Returns the network info
     * @return
     */
    public NetworkInfo getNetworkInfo(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Returns true if it's connected
     * @return
     */
    public boolean isConnected(){
        NetworkInfo info = getNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * Returns if it's connected via Wifi
     * @return
     */
    public boolean isConnectedWifi(){
        NetworkInfo info = getNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Returns if it's connected via Mobile Network
     * @return
     */
    public boolean isConnectedMobile(){
        NetworkInfo info = getNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}