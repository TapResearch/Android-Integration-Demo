package com.tapresearch.tapdemo.ui.placements;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tapresearch.tapdemo.R;


public class PlacementItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;


    public PlacementItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.placementIdText);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setColor(@ColorInt int color) {
        textView.setTextColor(color);
    }

}
