package com.example.pocketguard.Json;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pocketguard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Main activity class for JSON parsing and displaying data in a ListView.
 *
 * <p>This class demonstrates the use of the Singleton design pattern via the Volley RequestQueue
 * and follows the MVC (Model-View-Controller) design pattern for separating data handling, UI updates,
 * and user interactions.</p>
 */
public class MainActivity_Json extends AppCompatActivity {

    ListView listView;
    EditText et;
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_json);

        listView = findViewById(R.id.list);
        et = findViewById(R.id.et);

        // Initialize JSON parsing with input from EditText
        parsing(et.getText().toString());
    }
    /**
     * Initiates a network request to fetch JSON data.
     * @param val The string input to filter the data.
     */
    void parsing(String val){
        // Singleton pattern: Using a single instance of RequestQueue to handle network requests
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.online/v1/records/a085cf6d-8f70-4b1d-94c2-1f5ccef0900b";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    paseJson(response,val);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response

            }
        });

        requestQueue.add(stringRequest);

    }
    /**
     * Parses the JSON response and populates the ListView.
     * @param response The JSON response from the server.
     * @param val The string input to filter the data.
     * @throws JSONException If there is an error parsing the JSON.
     */
    void paseJson(String response,String val) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        ArrayList<String> club = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++){

            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            club.add(jsonObject1.getString("app"));
        }


        // MVC pattern: Updating the view with new data
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,club);
        listView.setAdapter(arrayAdapter);


        // Setting up item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String str = et.getText().toString();

                try {

                    if(str.equals("Ratings")){
                        JSONArray jsonArray1 = jsonArray.getJSONObject(pos).getJSONArray("ratings");
                        String name = jsonArray.getJSONObject(pos).getString("app");

                        Intent intent = new Intent(MainActivity_Json.this, Ratings.class);
                        intent.putExtra("ratings",jsonArray1.toString());
                        intent.putExtra("app_name",name);
                        startActivity(intent);
                    }else if(str.equals("Rank")){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(pos).getJSONObject("rank");

                        Intent intent = new Intent(MainActivity_Json.this, Rank.class);
                        intent.putExtra("rank",jsonObject1.toString());
                        startActivity(intent);

                    }else{

                        Toast.makeText(MainActivity_Json.this, "Wrong Input", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}