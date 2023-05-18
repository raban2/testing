package com.example.smart_selection_and_pest_controlapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {


    EditText N,P,K,temperature,humidity,ph,rainfall;
    Button predict;
    TextView result;
    String url = "http://127.0.0.1:5000/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        N = findViewById(R.id.N);
        P = findViewById(R.id.P);
        K = findViewById(R.id.K);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        ph = findViewById(R.id.ph);
        rainfall = findViewById(R.id.rainfall);

        predict = findViewById(R.id.Predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("label");
                                    if(data.equals("1")){
                                        result.setText("label");
                                    }else{
                                        result.setText("Placement Nahi Hoga");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("N",N.getText().toString());
                        params.put("P",P.getText().toString());
                        params.put("K",K.getText().toString());
                        params.put("temperature",temperature.getText().toString());
                        params.put("humidity",humidity.getText().toString());
                        params.put("ph",ph.getText().toString());
                        params.put("rainfall",rainfall.getText().toString());



                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}