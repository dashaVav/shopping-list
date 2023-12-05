package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private final ArrayList<Item> items;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ItemAdapter(ArrayList<Item> myDataset, OnItemClickListener listener) {
        items = myDataset;
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        final ItemViewHolder viewHolder = new ItemViewHolder(view);

        view.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder vh, int position) {
        vh.textViewItem.setText(getItemText(position));

        vh.checkBoxItem.setOnCheckedChangeListener(null);

        vh.checkBoxItem.setOnCheckedChangeListener(
                (buttonView, isChecked) -> items.get(position).setIsChecked(isChecked));
        vh.imageViewDelete.setOnClickListener(v -> {
            int adapterPosition = vh.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                removeItem(adapterPosition);
            }
        });

        vh.checkBoxItem.setChecked(items.get(position).getIsChecked());
    }

    public void addItem(String text) {
        items.add(new Item(text));
        notifyItemInserted(getItemCount() - 1);
    }

    public void updateItemText(int position, String newText) {
        items.get(position).setText(newText);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public String getItemText(int position) {
        return items.get(position).getText();
    }
}
