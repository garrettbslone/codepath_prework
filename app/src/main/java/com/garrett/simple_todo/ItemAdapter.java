/*
 * author: Garrett
 * date: 12/15/2020
 * project: todo_app
 * description:
 */
package com.garrett.todo_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<String> mItems;
    private OnLongClickListener mListener;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mText1;

        public ItemViewHolder (@NonNull View itemView) {
            super(itemView);
            mText1 = itemView.findViewById(android.R.id.text1);
        }

        public void bind (String item) {
            mText1.setText(item);
            mText1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick (View v) {
                    mListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public ItemAdapter (List<String> items, OnLongClickListener listener) {
        mItems = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ItemViewHolder holder, int position) {
        String item = mItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount () {
        return mItems.size();
    }
}