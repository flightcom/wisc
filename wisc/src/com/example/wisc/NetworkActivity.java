package com.example.wisc;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkActivity extends Activity {

    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        Bundle extra = getIntent().getExtras();
        String txtSsid = extra.getString("ssid");
        String txtBssid = extra.getString("bssid");

        l = (ListView) findViewById(android.R.id.list);

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> element = new HashMap<String, String>();
        element.put("text1", txtSsid);
        element.put("text2", txtBssid);
        list.add(element);

        ListAdapter adapter = new SimpleAdapter(this,
                //Valeurs à insérer
                list,
                android.R.layout.simple_list_item_2,
                new String[] {"text1", "text2"},
                new int[] {android.R.id.text1, android.R.id.text2 });

        l.setAdapter(adapter);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.network, menu);
        return true;
    }*/

}
