package com.example.queenieliu.smartclassroom.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.queenieliu.smartclassroom.R;

/**
 * Created by Queenie Liu on 2018/1/14.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    public String[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView studyCv;
        public TextView wordTv,descripTv;
        public ImageButton soundBtn;

        public ViewHolder(View v) {
            super(v);
            studyCv=(CardView)v.findViewById(R.id.studyCv);
            wordTv=(TextView)v.findViewById(R.id.wordTv);
            descripTv=(TextView)v.findViewById(R.id.descripTv);
           soundBtn=(ImageButton) v.findViewById(R.id.soundBtn);
        }

    }

    public CustomAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.study_main_item, viewGroup, false);
        return new ViewHolder(v);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.wordTv.setText(mDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}