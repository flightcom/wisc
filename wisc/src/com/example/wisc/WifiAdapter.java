package com.example.wisc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class WifiAdapter extends BaseAdapter {
 
	List<HashMap<String, String>> liste;
 
    public WifiAdapter(Context c, List<HashMap<String, String>> liste) {
    	
    	this.liste = liste;

    }
 
    @Override
    public Object getItem(int i) {
        return liste.get(i);    // single item in the list
    }
 
    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }
 
    @Override
    public View getView(int index, View view, final ViewGroup parent) {
 
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.activity_main_list_item, parent, false);
        }
 
        final HashMap<String, String> hm = liste.get(index);
        String ssid = hm.get("ssid");
        String bssid = hm.get("bssid");
        String capabilities = hm.get("capabilities");
        Float lvl = Float.parseFloat(hm.get("level"));
        String sec = null;
        
        if(capabilities.contains("WPA2")){
        	sec = "WPA2";
        } else if (capabilities.contains("WPA")){
        	sec = "WPA";
        } else if (capabilities.contains("WEP")){
        	sec = "WEP";
        } else {
        	sec = capabilities;
        }
        
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        textView1.setText(ssid);

        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setText(bssid);

        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setText(sec);

        TextView text1 = (TextView) view.findViewById(R.id.text1);
        text1.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 100-lvl));
        
        TextView text2 = (TextView) view.findViewById(R.id.text2);
        text2.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, (float) lvl));

        return view;
    }

	@Override
	public int getCount() {

		return liste.size();

	}
}