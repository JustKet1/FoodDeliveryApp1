package com.example.fooddeliveryapp1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        ArrayList<CartItem> selectedItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("selectedItems");
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        String address = getIntent().getStringExtra("address");

        // Display order information
        TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        totalPriceTextView.setText("Стоимость: " + totalPrice+" руб.");

        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText("Адрес доставки: " + address);

        // Display selected items using a RecyclerView
        RecyclerView recyclerView = findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(selectedItems);
        recyclerView.setAdapter(adapter);
    }
}


