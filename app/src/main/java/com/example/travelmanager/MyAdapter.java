package com.example.travelmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        FrameLayout flCateg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.category_name);
            flCateg = itemView.findViewById(R.id.root);
        }
    }

    List<Category> mCList;
    Context mContext;

    public MyAdapter(List<Category> clist, Context context) {
        mCList = clist;
        mContext = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categ_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final Category curItem = mCList.get(position);
        holder.tvName.setText(curItem.getName());
        holder.flCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mContext).onClickCalled(curItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCList.size();
    }
}
