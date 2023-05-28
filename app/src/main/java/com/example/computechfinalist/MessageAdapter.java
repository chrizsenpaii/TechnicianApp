package com.example.computechfinalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MesViewHolder> {
    private Context mCtxx;
    private List<mes> mesList;


    public MessageAdapter(Context mCtxx, List<mes> mesList) {
        this.mCtxx = mCtxx;
        this.mesList = mesList;


    }


    @Override
    public MesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtxx);
        View viewz = inflater.inflate(R.layout.message_list, null);
        return new MesViewHolder(viewz);
    }

    @Override
    public void onBindViewHolder(MesViewHolder holder, int position) {
        mes mess = mesList.get(position);
        holder.sender.setText(mess.getSender());
        holder.receiver.setText(mess.getReceiver());
        holder.content.setText(mess.getContent());
    }

    @Override
    public int getItemCount() {

        return mesList.size();
    }

    class MesViewHolder extends RecyclerView.ViewHolder {
        TextView sender, receiver, content;


        public MesViewHolder(View itemviews) {
            super(itemviews);
            sender = itemviews.findViewById(R.id.tvtechsenders);
            receiver = itemviews.findViewById(R.id.tvtechreceivers);
            content = itemviews.findViewById(R.id.tvtechmessages);

        }
    }
}
