package com.example.karabressler.summit_hack_app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reimaginebanking.api.nessieandroidsdk.models.Bill;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements ViewHolderCallbacks {
    private ArrayList<Bill> mDataset;
    private AdapterCallbacks<Bill> mAdapterCallback;

    @Override
    public void onItemClick(View view, int position) {
        mAdapterCallback.onItemClicked(mDataset.get(position));
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Bill> myDataset, AdapterCallbacks myCallbacks) {
        mDataset = myDataset;
        mAdapterCallback = myCallbacks;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mRequest.setText(mDataset.get(position).getNickname());

        String temp = Double.toString(mDataset.get(position).getPaymentAmount());
        temp = temp.substring(0, temp.length() - 2);
        holder.mAmount.setText(temp);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView mProfile;
        public TextView mRequest;
        public TextView mAmount;

        public ViewHolderCallbacks mCallbacks;


        public ViewHolder(View v, ViewHolderCallbacks onItemClickCallback) {
            super(v);
            mProfile = (ImageView) v.findViewById(R.id.profile);
            mRequest = (TextView) v.findViewById(R.id.request);
            mAmount = (TextView) v.findViewById(R.id.amount);

            mCallbacks = onItemClickCallback;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onItemClick(v, getAdapterPosition());
        }
    }
}