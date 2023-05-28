package com.example.computechfinalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class hisAdapter extends RecyclerView.Adapter<hisAdapter.MyViewHolder> {
    private Context mCtx;
    private List<hist> historylist;


    public hisAdapter(Context mCtx, List<hist> historylist) {
        this.mCtx = mCtx;
        this.historylist = historylist;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.history_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        hist history = historylist.get(position);
        holder.drivers.setText(history.getDriver());
        holder.prices.setText(history.getPrice());
        holder.dates.setText(history.getDate());
        holder.places.setText(history.getPlace());

    }

    @Override
    public int getItemCount() {

        return historylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView drivers;
        TextView places;
        TextView dates;
        TextView prices;

        public MyViewHolder(View itemview) {
            super(itemview);
            drivers = itemview.findViewById(R.id.tvtechdriversh);
            places = itemview.findViewById(R.id.tvtechPlacesh);
            dates = itemview.findViewById(R.id.tvtechDatesh);
            prices = itemview.findViewById(R.id.tvtechPricesh);
        }
    }
}
