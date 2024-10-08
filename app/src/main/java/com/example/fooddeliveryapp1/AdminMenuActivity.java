package com.example.fooddeliveryapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class AdminMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu_page);
        Button addButton = findViewById(R.id.menu_add);
        Button delButton = findViewById(R.id.menu_del);
        Button addAdminButton = findViewById(R.id.admin_add);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent (AdminMenuActivity.this, AdminMainActivity.class);
            startActivity(intent);
        });
        delButton.setOnClickListener(view -> {
            Intent intent = new Intent (AdminMenuActivity.this, DeletePositionActivity.class);
            startActivity(intent);
        });
        addAdminButton.setOnClickListener(view -> {
            Intent intent = new Intent (AdminMenuActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
