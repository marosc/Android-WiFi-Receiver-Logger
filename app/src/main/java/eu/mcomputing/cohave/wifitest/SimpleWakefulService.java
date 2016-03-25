package eu.mcomputing.cohave.wifitest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SimpleWakefulService extends IntentService {
    public SimpleWakefulService() {
        super("SimpleWakefulService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // At this point SimpleWakefulReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.  This sample just does some slow work,
        // but more complicated implementations could take their own wake
        // lock here before releasing the receiver's.
        //
        // Note that when using this approach you should be aware that if your
        // service gets killed and restarted while in the middle of such work
        // (so the Intent gets re-delivered to perform the work again), it will
        // at that point no longer be holding a wake lock since we are depending
        // on SimpleWakefulReceiver to that for us.  If this is a concern, you can
        // acquire a separate wake lock here.
        if (intent!=null){
            ArrayList<Parcelable> list =  intent.getParcelableArrayListExtra("WIFI_LIST");


            File file;
            FileOutputStream outputStream;
            try {
                file = new File(Environment.getExternalStorageDirectory(), "mywifis");

                outputStream = new FileOutputStream(file,true);
                outputStream.write(("Cas "+SystemClock.elapsedRealtime()+"\n").getBytes());
                for (Parcelable p : list){
                    ScanResult r = (ScanResult) p;
                    outputStream.write((p.toString()+"\n").getBytes());
                }
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Log.d("intenty", "Completed service @ " + SystemClock.elapsedRealtime());
        NetworkChangeWakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}