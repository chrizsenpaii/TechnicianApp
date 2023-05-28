package com.example.computechfinalist;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class techAdapter extends RecyclerView.Adapter<techAdapter.techViewHolder> {

    private Context mCtx;
    private List<Technician> techList;
    private onItemClickListener mListener;

    public void setOnItemClickListener(onItemClickListener listener){
            mListener =listener;
    }


    public techAdapter(Context mCtx, List<Technician> techList) {
        this.mCtx = mCtx;
        this.techList = techList;

    }

    @Override
    public techViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tech_list, null);
        return new techViewHolder(view);
    }

    @Override
    public void onBindViewHolder(techViewHolder holder, int position) {
        Technician technician = techList.get(position);



        holder.tvtechname.setText(technician.getName());
        holder.tvtechusername.setText(technician.getUsername());
        holder.tvtechemail.setText(technician.getEmail());
        holder.tvtechcity.setText(technician.getCity());
        holder.tvtechphone.setText(String.valueOf(technician.getPhone()));

    }

    @Override
    public int getItemCount() {
        return techList.size();
    }




    class techViewHolder extends RecyclerView.ViewHolder {

        TextView tvtechid, tvtechname, tvtechusername, tvtechemail, tvtechcity, tvtechphone;


        public techViewHolder(View itemViews) {
            super(itemViews);


            tvtechname = itemView.findViewById(R.id.tvtechname);
            tvtechusername = itemView.findViewById(R.id.tvtechusername);
            tvtechemail = itemView.findViewById(R.id.tvtechemail);
            tvtechcity = itemView.findViewById(R.id.tvtechcity);
            tvtechphone = itemView.findViewById(R.id.tvtechphone);
            itemViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
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
