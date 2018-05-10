package com.krito.io.oscar.application;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.krito.io.oscar.model.operations.UserOperation;
import com.krito.io.oscar.receivers.ConnectivityReceiver;

/**
 * Created by Ahmed Ali on 3/19/2018.
 */

public class OscarApplication extends Application {

    private static OscarApplication mInstance;
    private ConnectivityReceiver receiver;
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        receiver = new ConnectivityReceiver();
        registerReceiver(receiver, filter);

        UserOperation.setRequestQueue(getRequestQueue());
    }

    public static synchronized OscarApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);

        return requestQueue;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(receiver);
    }
}
