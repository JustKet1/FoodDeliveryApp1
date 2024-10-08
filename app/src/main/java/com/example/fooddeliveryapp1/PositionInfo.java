package com.example.fooddeliveryapp1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PositionInfo extends AppCompatActivity {
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_info);
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        int userId = settings.getInt(LoginActivity.USER_ID_KEY, -1);

        TextView nameTextView = findViewById(R.id.nameText);
        TextView descriptionTextView = findViewById(R.id.descriptionText);
        TextView priceTextView = findViewById(R.id.priceText);
        Button buttonOrder = findViewById(R.id.addToOrder);

        String selectedItemName = getIntent().getStringExtra("selected_item_name");
        String selectedItemDescription = getIntent().getStringExtra("selected_item_description");
        String selectedItemPrice = getIntent().getStringExtra("selected_item_price");
        String selectedItemId = getIntent().getStringExtra("selected_item_id");


        nameTextView.setText(selectedItemName);
        descriptionTextView.setText(selectedItemDescription);
        priceTextView.setText(selectedItemPrice);


        buttonOrder.setOnClickListener(view -> {
            SQL_Controller sql_con = new SQL_Controller();
            connection = sql_con.connectionClass();
            try {
                String sqlStatement = "Insert into [deliveryDB].[dbo].[cart_table] (POSITION_ID, USER_ID, QUANTITY) VALUES (?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sqlStatement);
                ps.setString(1, selectedItemId);
                ps.setString(2, String.valueOf(userId));
                ps.setString(3, "1");
                ps.executeUpdate();
                Toast.makeText(this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        });

    }

}
