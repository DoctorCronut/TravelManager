package com.example.travelmanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class RVDataAdapter extends RecyclerView.Adapter<RVDataAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvExpense;
        TextView tvName;
        TextView tvTitle;
        RecyclerView rvList;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.categ_title);
            tvDate = itemView.findViewById(R.id.date);
            tvExpense = itemView.findViewById(R.id.expense);
            tvName = itemView.findViewById(R.id.desc);
            rvList = itemView.findViewById(R.id.rv_data);
        }
    }

    List<Data> mDatas;
    Context mContext;
    Intent mIntent;

    public RVDataAdapter(Context context, List<Data> datas, Intent intent) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public RVDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.data_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVDataAdapter.ViewHolder holder, int position) {
        if (!mDatas.isEmpty()) {
            Log.wtf("?", "help");
            Data data = mDatas.get(position);
            Log.wtf("?", "help" + data.getmName());
            holder.tvName.setText(data.getmName());
            holder.tvExpense.setText(String.format(Locale.US, "$%.2f", data.getmExpense()));
            holder.tvDate.setText(data.getmDate());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
