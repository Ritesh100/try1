package com.example.try1;

import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    TextView macIdTV;
    String mac;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        macIdTV = findViewById(R.id.mac_id);

        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        final String MAC_ADDRESS = wifiInfo.getMacAddress();
        Log.d(TAG, "MAC_ADDRESS"+ MAC_ADDRESS);
        macIdTV.setText(MAC_ADDRESS);

        macIdTV.setVisibility(View.VISIBLE);
        postDataUsingVolley(macIdTV.getText().toString());
        //try
        postDataUsingVolley(MAC_ADDRESS);
    }

    private void postDataUsingVolley(String macAddress){
        String url =" http://116.90.235.210/times/api/checkMac.php";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST,url,
                new com.android.volley.Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                macIdTV.setText("");

                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
         try {
             JSONObject respObj = new JSONObject(response);
             String macAddress = respObj.getString("macAddress");
             Log.d(TAG, "onResponse: "+macAddress);
             macIdTV.setText("macAddress"+ macAddress);
         }catch(JSONException e){
             e.printStackTrace();
         }
            }

        }, error -> Toast.makeText(MainActivity.this,"Fail to get Response="+error,
                Toast.LENGTH_SHORT).show()){

        };
        queue.add(request);
    }
}
