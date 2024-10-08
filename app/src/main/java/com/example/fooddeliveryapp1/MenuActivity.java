package com.example.fooddeliveryapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class MenuActivity extends AppCompatActivity {
    private String getDescriptionFromDatabase(String itemName) {
        String description = "";
        try {
            SQL_Controller sql_con = new SQL_Controller();
            Connection connection = sql_con.connectionClass();
            String sqlStatement = "SELECT * FROM [deliveryDB].[dbo].[menu_table] WHERE NAME = ?";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setString(1, itemName);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                description = set.getString("DESCRIPTION");
            }
            set.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return description;
    }

    private String getPriceFromDatabase(String itemName) {
        String price = "";
        try {
            SQL_Controller sql_con = new SQL_Controller();
            Connection connection = sql_con.connectionClass();
            String sqlStatement = "SELECT * FROM [deliveryDB].[dbo].[menu_table] WHERE NAME = ?";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setString(1, itemName);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                price = set.getString("PRICE");
            }
            set.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return price;
    }
    private String getIDFromDatabase(String itemName) {
        String id = "";
        try {
            SQL_Controller sql_con = new SQL_Controller();
            Connection connection = sql_con.connectionClass();
            String sqlStatement = "SELECT * FROM [deliveryDB].[dbo].[menu_table] WHERE NAME = ?";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setString(1, itemName);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                id = set.getString("ID");
            }
            set.close();
            ps.close();
            connection.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return id;
    }
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        ListView listView = findViewById(R.id.menu_list);
        SQL_Controller sql_con = new SQL_Controller();
        connection = sql_con.connectionClass();
        try {
            String sqlStatement = "Select * from [deliveryDB].[dbo].[menu_table]";
            Statement smt = connection.createStatement();
            ResultSet set = smt.executeQuery(sqlStatement);
            ArrayList<String> list = new ArrayList<String>();
            while (set.next()) {
                String str = set.getString("NAME");
                list.add(str);
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(MenuActivity.this,
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(arrayAdapter);
            connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItemName = (String) parent.getItemAtPosition(position);
            String selectedItemDescription = getDescriptionFromDatabase(selectedItemName);
            String selectedItemPrice = getPriceFromDatabase(selectedItemName);
            String selectedItemID = getIDFromDatabase(selectedItemName);
            Intent intent = new Intent(MenuActivity.this, PositionInfo.class);
            intent.putExtra("selected_item_name", selectedItemName);
            intent.putExtra("selected_item_description", selectedItemDescription);
            intent.putExtra("selected_item_price", selectedItemPrice);
            intent.putExtra("selected_item_id", selectedItemID);
            startActivity(intent);
        });
    }


}
