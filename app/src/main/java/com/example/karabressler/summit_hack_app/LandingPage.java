package com.example.karabressler.summit_hack_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Bill;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity implements AdapterCallbacks<Bill> {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_landing_page);

        NessieClient client = NessieClient.getInstance("7a7490a0996b5b7d04f1c7315804c6fd");
        client.BILL.getBillsByCustomer("58000d41360f81f104543d1c", new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                ArrayList<Bill> bills = (ArrayList<Bill>) result;
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                mRecyclerView.setHasFixedSize(true);

                mLayoutManager = new LinearLayoutManager(LandingPage.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new MyAdapter(bills, LandingPage.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(NessieError error) {

            }
        });

//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://api.reimaginebanking.com/accounts?key=7a7490a0996b5b7d04f1c7315804c6fd";
//
//        JsonArrayRequest jsArrRequest = new JsonArrayRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        System.out.println("hello, i'm here");
//                        ArrayList<JSONObject> myDataset = new ArrayList<JSONObject>();
//                        for (int j = 0; j < response.length(); j++) {
//                            try {
//                                myDataset.add((JSONObject) response.get(j));
//                            } catch (Exception e) {
//                                e.getMessage();
//                            }
//
//                        }
//                        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//                        // use this setting to improve performance if you know that changes
//                        // in content do not change the layout size of the RecyclerView
//                        mRecyclerView.setHasFixedSize(true);
//
//                        // use a linear layout manager
//                        mLayoutManager = new LinearLayoutManager(LandingPage.this);
//                        mRecyclerView.setLayoutManager(mLayoutManager);
//
//
//                        // specify an adapter (see also next example)
//                        mAdapter = new MyAdapter(myDataset, LandingPage.this);
//                        mRecyclerView.setAdapter(mAdapter);
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("bye, not there");
//                        System.out.println(error.getMessage());
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//        queue.add(jsArrRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, CreateWish.class);
                intent.putExtra("CURRENT USER ID", "5877fe9b1756fc834d8eb2ea");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClicked(Bill bill) {
        Intent intent = new Intent(this, DisplayRequestActivity.class);
        intent.putExtra("ACCOUNT ID", bill.getAccountId());
        intent.putExtra("BILL ID", bill.getId());
        intent.putExtra("CURRENT USER ID", "5877fe9b1756fc834d8eb2ea");

        startActivity(intent);
        Log.d("asdf", "hadkdfhkdjhf");
    }
}
