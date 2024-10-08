package com.example.fooddeliveryapp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminMainActivity extends AppCompatActivity {

    Connection connection;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        SQL_Controller sql_con = new SQL_Controller();
        connection = sql_con.connectionClass();

        EditText editName = findViewById(R.id.editTextName);
        EditText editPrice = findViewById(R.id.editTextPrice);
        EditText editDescription = findViewById(R.id.editTextDescription);
        Button button = findViewById(R.id.addButton);

        button.setOnClickListener(view -> {

            if (editName.toString().isEmpty()){
                Toast.makeText(AdminMainActivity.this, "Ошибка занесения в базу данных", Toast.LENGTH_SHORT).show();
            }
            else {
                String Price = editPrice.getText().toString();
                String Name = editName.getText().toString();
                String Description = editDescription.getText().toString();
                try {
                    String sqlStatement = "Insert into [deliveryDB].[dbo].[menu_table] (NAME, PRICE, DESCRIPTION) VALUES (?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sqlStatement);
                    ps.setString(1, Name);
                    ps.setString(2, Price);
                    ps.setString(3, Description);
                    ps.executeUpdate();
                    Toast.makeText(AdminMainActivity.this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
                    connection.close();
                }
                catch (Exception e){
                    Log.e("Ошибка: ", e.getMessage());
                }
            }

        });

    }
}
