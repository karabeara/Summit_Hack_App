package com.example.karabressler.summit_hack_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity implements AdapterCallbacks{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    String[] mStringArray;
//    CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);



        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.reimaginebanking.com/accounts?key=7a7490a0996b5b7d04f1c7315804c6fd";

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("hello, i'm here");
                        ArrayList<JSONObject> myDataset = new ArrayList<JSONObject>();
                        for (int j = 0; j < response.length(); j++) {
                            try{
                                myDataset.add((JSONObject) response.get(j));
                            } catch (Exception e) {
                                e.getMessage();
                            }

                        }
                        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        mRecyclerView.setHasFixedSize(true);

                        // use a linear layout manager
                        mLayoutManager = new LinearLayoutManager(LandingPage.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);


                        // specify an adapter (see also next example)
                        mAdapter = new MyAdapter(myDataset, LandingPage.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("bye, not there");
                        System.out.println(error.getMessage());
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsArrRequest);
    }

    @Override
    public void onItemClicked(Object object, View sharedView) {
        Log.d("a", "we are here");
    }
}
