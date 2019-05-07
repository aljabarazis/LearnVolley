package com.aljabar.learnvolley;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String id, name, username, email, street, suite, city, zipcode, address,  geo, lat, lng;
    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private ArrayList<Users> usersArrayList;
    private Button btnGetData;
    private BufferedReader reader = null;
    private HttpURLConnection connection = null;
    ProgressDialog progressDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView) ;
        mSwipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        rq = Volley.newRequestQueue(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makeJsonArrayRequest();
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonArrayRequest();
            }
        });
    }

    public void makeJsonArrayRequest(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String reqURL = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, reqURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                usersArrayList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        //create a JSONObject for fetching single user data
                        JSONObject userDetail = response.getJSONObject(i);
                        id = userDetail.getString("id");
                        name = userDetail.getString("name");
                        username = userDetail.getString("username");
                        email = userDetail.getString("email");

                        JSONObject addressDetail = userDetail.getJSONObject("address");
                        street = addressDetail.getString("street");
                        suite = addressDetail.getString("suite");
                        city = addressDetail.getString("city");
                        zipcode = addressDetail.getString("zipcode");
                        address = street+", "+suite+", "+city+", "+zipcode;

                        JSONObject LatsDetail = addressDetail.getJSONObject("geo");
                        lat = LatsDetail.getString("lat");
                        lng = LatsDetail.getString("lng");
                        geo = lat+" "+lng;

                        usersArrayList.add(new Users(id, name, username, email, address, geo));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();

                adapter = new UsersAdapter(usersArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();

                Log.i("Volley error :", String.valueOf(error));
            }
        });

        rq.add(jsonArrayRequest);
    }
}
