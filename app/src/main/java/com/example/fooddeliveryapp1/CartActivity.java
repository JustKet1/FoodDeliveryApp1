package com.example.fooddeliveryapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartItem> cartItems;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        int userId = settings.getInt(LoginActivity.USER_ID_KEY, -1);

        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartItems);

        ListView listView = findViewById(R.id.cartListView);
        listView.setAdapter(cartAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            CartItem cartItem = cartItems.get(position);
            cartItem.setSelected(!cartItem.isSelected());
            cartAdapter.notifyDataSetChanged();
        });

        try {
            SQL_Controller sql_con = new SQL_Controller();
            Connection connection = sql_con.connectionClass();
            String sqlStatement = "SELECT m.NAME, m.PRICE, c.QUANTITY FROM dbo.cart_table c JOIN dbo.menu_table m ON c.POSITION_ID = m.ID WHERE c.USER_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setString(1, String.valueOf(userId));
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                String name = set.getString("NAME");
                int price = set.getInt("PRICE");
                int quantity = set.getInt("QUANTITY");
                cartItems.add(new CartItem(name, price, quantity));
            }
            set.close();
            ps.close();
            connection.close();

            cartAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        Button orderSubmit = findViewById(R.id.orderButton);
        orderSubmit.setOnClickListener(view -> {
            ArrayList<CartItem> selectedItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                if (item.isSelected()) {
                    selectedItems.add(item);
                }
            }

            // Calculate total price
            int totalPrice = 0;
            for (CartItem item : selectedItems) {
                totalPrice += item.getPrice() * item.getQuantity();
            }

            String address = "User's address";

            // Save order information to the server
            saveOrderToServer(userId, totalPrice, address);

            // Navigate to OrderActivity
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            intent.putExtra("selectedItems", selectedItems);
            intent.putExtra("totalPrice", totalPrice);
            intent.putExtra("address", address);
            startActivity(intent);
        });
    }
    private void saveOrderToServer(int userId, int totalPrice, String address) {
        try {
            SQL_Controller sql_con = new SQL_Controller();
            Connection connection = sql_con.connectionClass();
            String sqlStatement = "INSERT INTO dbo.order_table (USER_ID, ORDER_STATUS, ORDER_ADDRESS, TOTAL_PRICE, ORDER_DATE) VALUES (?, ?, ?, ?, GETDATE())";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setInt(1, userId);
            ps.setString(2, "Pending"); // You can set the initial order status here
            ps.setString(3, address);
            ps.setInt(4, totalPrice);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

}

