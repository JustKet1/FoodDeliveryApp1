package com.example.fooddeliveryapp1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<CartItem> selectedItems;

    public OrderAdapter(ArrayList<CartItem> selectedItems) {
        this.selectedItems = selectedItems;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        CartItem item = selectedItems.get(position);
        holder.itemNameTextView.setText(item.getName());
        holder.itemQuantityTextView.setText("Количество: " + item.getQuantity());
        holder.itemPriceTextView.setText("Цена: " + item.getPrice()+" руб.");
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemQuantityTextView;
        TextView itemPriceTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
        }
    }
}

