package com.example.shoppinglist;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public CheckBox checkBoxItem;
    public TextView textViewItem;
    public ImageView imageViewDelete;

    public ItemViewHolder(View v) {
        super(v);
        checkBoxItem = v.findViewById(R.id.checkBoxItem);
        textViewItem = v.findViewById(R.id.text_item);
        imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
    }
}

