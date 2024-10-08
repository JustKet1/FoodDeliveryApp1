package com.example.fooddeliveryapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs";
    public static final String USER_ID_KEY = "userId";

    Connection connection;
    Map<String, UserInfo> userCredentials = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        EditText login = findViewById(R.id.editTextLogin);
        EditText password = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.login_button);
        Button regButton = findViewById(R.id.register_button);

        SQL_Controller sql_con = new SQL_Controller();
        connection = sql_con.connectionClass();

        try {
            String sqlStatement = "SELECT LOGIN, STATUS, ID FROM [deliveryDB].[dbo].[user_table]";
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                String loginStr = set.getString("LOGIN");
                String status = set.getString("STATUS");
                int id = set.getInt("ID");
                userCredentials.put(loginStr, new UserInfo(status, id));
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        loginButton.setOnClickListener(view -> {
            String enteredLogin = login.getText().toString();
            String enteredPassword = password.getText().toString();

            if (enteredLogin.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                UserInfo userInfo = userCredentials.get(enteredLogin);
                if (userInfo == null) {
                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    login.setText("");
                    password.setText("");
                } else {
                    // Save the user's ID in SharedPreferences
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt(USER_ID_KEY, userInfo.getId());
                    editor.apply();

                    Intent intent;
                    switch (userInfo.getStatus()) {
                        case "user":
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            break;
                        case "admin":
                            intent = new Intent(LoginActivity.this, AdminMenuActivity.class);
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                            login.setText("");
                            password.setText("");
                            return;
                    }
                    startActivity(intent);
                }
            }
        });

        regButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private static class UserInfo {
        private final String status;
        private final int id;

        public UserInfo(String status, int id) {
            this.status = status;
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public int getId() {
            return id;
        }
    }
}
