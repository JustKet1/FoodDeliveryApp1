package com.example.fooddeliveryapp1;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterActivity extends AppCompatActivity {

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SQL_Controller sql_con = new SQL_Controller();
        connection = sql_con.connectionClass();

        EditText name = findViewById(R.id.editTextTextPersonName);
        EditText login = findViewById(R.id.editTextTextEmailAddress2);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editTextTextPassword2);
        Button reg_finish = findViewById(R.id.reg_finish);

        reg_finish.setOnClickListener(view -> {
            String regName = name.getText().toString();
            String regNumber = phone.getText().toString();
            String regLogin = login.getText().toString();
            String regPass = password.getText().toString();
            if (regName.isEmpty() || regNumber.isEmpty() || regLogin.isEmpty() || regPass.isEmpty()){
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    String sqlStatement = "Insert into [deliveryDB].[dbo].[user_table] (LOGIN, PASSWORD, STATUS, NAME, NUMBER) VALUES (?,?,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sqlStatement);
                    ps.setString(1, regLogin);
                    ps.setString(2, regPass);
                    ps.setString(3, "user");
                    ps.setString(4, regName);
                    ps.setString(5, regNumber);
                    ps.executeUpdate();
                    Toast.makeText(this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
                    connection.close();
                }
                catch (Exception e){
                    Log.e("Ошибка: ", e.getMessage());
                }
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}