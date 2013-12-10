package com.example.wisc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.compare;

public class MainActivity extends Activity {


    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    ListView vue;
    StringBuilder sb = new StringBuilder();
    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //On récupère une ListView de notre layout en XML, c'est la vue qui représente la liste
        vue = (ListView) findViewById(R.id.mylist);

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        if(mainWifi.isWifiEnabled()==false)
        {
            mainWifi.setWifiEnabled(true);
        }

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mainWifi.startScan();
        
        doInback();
        
    }

    public void doInback()
    {
        handler.postDelayed(new Runnable() {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                receiverWifi = new WifiReceiver();
                registerReceiver(receiverWifi, new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mainWifi.startScan();
                doInback();
            }
        }, 200);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mainWifi.startScan();

        return super.onMenuItemSelected(featureId, item);
    }

    class WifiReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {

            ArrayList<String> connections=new ArrayList<String>();
            List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> element;

            sb = new StringBuilder();
            List<ScanResult> wifiList;
            wifiList = mainWifi.getScanResults();
            for(int i = 0; i < wifiList.size(); i++)
            {
                connections.add(wifiList.get(i).SSID);
                element = new HashMap<String, String>();
                element.put("ssid", wifiList.get(i).SSID);
                element.put("bssid", wifiList.get(i).BSSID);
                element.put("level", String.valueOf(2*(wifiList.get(i).level+100)));
                element.put("capabilities", wifiList.get(i).capabilities);
                liste.add(element);
            }

            // Sorting networks by level
            Collections.sort(liste, new MyMapComparator());


            ListAdapter adapter = new WifiAdapter(c, liste);
            vue.setAdapter(adapter);

            final String TAG_LEVEL = "Level";

            vue.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        // TODO Auto-generated method stub
                        Object o = vue.getItemAtPosition(position);
                        String pen = o.toString();

                        TextView text1 = (TextView) view.findViewById(R.id.textView1);
                        TextView text2 = (TextView) view.findViewById(R.id.textView2);
                        String txt1 = text1.getText().toString().trim();
                        String txt2 = text2.getText().toString().trim();
                        //Toast.makeText(getApplicationContext(), "You have chosen the pen: " + " " + pen, Toast.LENGTH_LONG).show();

                        Intent myIntent = new Intent(getApplicationContext(), NetworkActivity.class);
                        myIntent.putExtra("ssid", txt1); //Optional parameters
                        myIntent.putExtra("bssid", txt2); //Optional parameters
                        startActivity(myIntent);
                    }
                }
            );
        }
    }
    

    

    public class MyMapComparator implements Comparator<HashMap <String, String>>
    {
        @Override
        public int compare (HashMap<String, String> o1, HashMap<String, String> o2)
        {
            return Integer.compare(Integer.parseInt(o2.get("level")), Integer.parseInt(o1.get("level")));
        }
    }

}
