package eu.mcomputing.cohave.wifitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NetworkChangeWakefulBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.d("intenty", intent.getAction());




        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            int networkType = intent.getIntExtra(
                    ConnectivityManager.EXTRA_NETWORK_TYPE, -1);
            if (ConnectivityManager.TYPE_WIFI == networkType) {
                NetworkInfo networkInfo = (NetworkInfo) intent
                        .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null) {
                    if (networkInfo.isConnected()) {

                        // e.g. To check the Network Name or other info:
                        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        Log.d("intenty","conected to : "+wifiInfo.getSSID()+" : "+wifiInfo.getBSSID());
                    } else {
                        Log.d("intenty","disconected");
                    }
                }
            }

        }else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())){

            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            ArrayList<ScanResult> list = new ArrayList<>(wifiManager.getScanResults());

            // This is the Intent to deliver to our service.
            Intent service = new Intent(context, SimpleWakefulService.class);
            service.putParcelableArrayListExtra("WIFI_LIST",list);
            // Start the service, keeping the device awake while it is launching.
            Log.d("intenty", "Starting service @ " + SystemClock.elapsedRealtime());
            startWakefulService(context, service);

        }
    }
}