package com.example.fooddeliveryapp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DeletePositionActivity extends AppCompatActivity {
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pos);

        ListView listView = findViewById(R.id.pos_list);
        SQL_Controller sql_con = new SQL_Controller();
        connection = sql_con.connectionClass();

        try {
            String sqlStatement = "Select * from [deliveryDB].[dbo].[menu_table]";
            Statement smt = connection.createStatement();
            ResultSet set = smt.executeQuery(sqlStatement);

            ArrayList<String> list = new ArrayList<>();
            while (set.next()) {
                String str = set.getString("NAME");
                list.add(str);
            }

            PosAdapter adapter = new PosAdapter(DeletePositionActivity.this, list);
            listView.setAdapter(adapter);

            findViewById(R.id.deleteButton).setOnClickListener(v -> {
                boolean[] checked = adapter.getChecked();
                try {
                    String sqlDelete = "DELETE FROM [deliveryDB].[dbo].[menu_table] WHERE NAME = ?";
                    PreparedStatement stmt = connection.prepareStatement(sqlDelete);

                    for (int i = 0; i < checked.length; i++) {
                        if (checked[i]) {
                            stmt.setString(1, list.get(i));
                            stmt.addBatch();
                        }
                    }

                    stmt.executeBatch();
                    connection.close();
                    finish();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }
}
