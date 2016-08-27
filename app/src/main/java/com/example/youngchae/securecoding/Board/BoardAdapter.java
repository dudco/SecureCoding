package com.example.youngchae.securecoding.Board;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youngchae.securecoding.R;

import java.util.ArrayList;

/**
 * Created by dudco on 2016. 8. 27..
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MyViewHolder>{
    ArrayList<BoardList> items = new ArrayList<>();

    public BoardAdapter(ArrayList<BoardList> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BoardList item = items.get(position);

        holder.text_name.setText(item.getName());
        holder.text_date.setText(item.getDate());
        holder.text_desc.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_name;
        TextView text_desc;
        TextView text_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_date = (TextView) itemView.findViewById(R.id.text_item_date);
            text_name = (TextView) itemView.findViewById(R.id.text_item_name);
            text_desc = (TextView) itemView.findViewById(R.id.text_item_desc);
        }
    }
}
