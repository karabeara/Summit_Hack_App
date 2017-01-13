package com.example.karabressler.summit_hack_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<JSONObject> mDataset;
    private AdapterCallbacks<JSONObject> mCallback;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView mProfile;
        public TextView mRequest;
        public TextView mAmount;

        public ViewHolder(View v) {
            super(v);
            mProfile = (ImageView) v.findViewById(R.id.profile);
            mRequest = (TextView) v.findViewById(R.id.request);
            mAmount = (TextView) v.findViewById(R.id.amount);
        }

        @Override
        public void onClick(View v) {
            mCallback.onItemClicked(mDataset.get(getAdapterPosition()), v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<JSONObject> myDataset, AdapterCallbacks<JSONObject> callback) {
        mDataset = myDataset;
        mCallback = callback;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            holder.mRequest.setText((String) mDataset.get(position).get("nickname"));
            holder.mAmount.setText(Integer.toString((Integer) mDataset.get(position).get("balance")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}