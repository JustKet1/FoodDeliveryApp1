package com.example.fooddeliveryapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private ArrayList<CartItem> cartItems;
    private Context context;

    public CartAdapter(Context context, ArrayList<CartItem> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CartItem cartItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item_layout, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        TextView quantityTextView = convertView.findViewById(R.id.quantityTextView);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        nameTextView.setText(cartItem.getName());
        priceTextView.setText(String.valueOf(cartItem.getPrice()));
        quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        checkBox.setChecked(cartItem.isSelected());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cartItems.get(position).setSelected(isChecked);
        });

        return convertView;
    }
}

