package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    TextView forecastTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
    }

    public void get(View view){
        String apikeys="8aed87de770cb881ab564c64800c6548";
        String city=et.getText().toString();
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=8aed87de770cb881ab564c64800c6548";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("main");
                    String temperature= object.getString("temp");
                    Double temp=Double.parseDouble(temperature)-273.15;
                    tv.setText("Temperature : "+temp.toString().substring(0,5)+"c");
                    // tv.setText(temperature);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(request);
    }
}