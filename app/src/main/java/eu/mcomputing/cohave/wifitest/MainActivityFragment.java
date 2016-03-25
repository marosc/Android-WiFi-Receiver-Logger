package eu.mcomputing.cohave.wifitest;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            File file = new File(Environment.getExternalStorageDirectory(), "mywifis");
            FileInputStream fIn = null;
            try {
                fIn = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader myReader = null;
            if (fIn != null) {
                myReader = new BufferedReader(
                        new InputStreamReader(fIn));
            }
            String aDataRow = "";
            String aBuffer = "";
            try {
                if (myReader != null) {
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow + "\n";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextView textView = (TextView) view.findViewById(R.id.mytext);
            textView.setText(aBuffer);

            try {
                if (myReader != null) {
                    myReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch ( Exception e){

        }

    }
}
