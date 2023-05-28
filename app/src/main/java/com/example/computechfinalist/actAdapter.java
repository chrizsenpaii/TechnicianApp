package com.example.computechfinalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class actAdapter extends RecyclerView.Adapter<actAdapter.ActHolder> {
    private Context mCtx;
    private List<acts> actlist;
    private onItemClickListener mListeners;
    public void setOnItemClickListener(onItemClickListener listener){
        mListeners =listener;
    }
    public actAdapter(Context mCtx, List<acts> actlist) {
        this.mCtx = mCtx;
        this.actlist = actlist;


    }


    @Override
    public ActHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_list, null);
        return new ActHolder(view);
    }

    @Override
    public void onBindViewHolder(ActHolder holder, int position) {
        acts history = actlist.get(position);
        if (history.getDriverx() != null) {
            holder.driversy.setText(history.getDriverx());
        } else {
            holder.driversy.setText("");
        }

        if (history.getPricex() != null) {
            holder.pricesy.setText(history.getPricex());
        } else {
            holder.pricesy.setText("");
        }

        if (history.getDatex() != null) {
            holder.datesy.setText(history.getDatex());
        } else {
            holder.datesy.setText("");
        }

        if (history.getPlacex() != null) {
            holder.placesy.setText(history.getPlacex());
        } else {
            holder.placesy.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return actlist.size();
    }

    class ActHolder extends RecyclerView.ViewHolder {
        TextView driversy;
        TextView placesy;
        TextView datesy;
        TextView pricesy;

        public ActHolder(View itemview) {
            super(itemview);
            driversy = itemview.findViewById(R.id.tvtechdriverz);
            placesy = itemview.findViewById(R.id.tvtechPlacez);
            datesy = itemview.findViewById(R.id.tvtechDatez);
            pricesy = itemview.findViewById(R.id.tvtechPricez);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListeners != null){
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListeners.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public  interface  onItemClickListener{
        void onItemClick(int position);


    }

}