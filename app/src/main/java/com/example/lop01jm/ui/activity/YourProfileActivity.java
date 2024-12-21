package com.example.lop01jm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lop01jm.R;

public class YourProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_profile);

        Button button = findViewById(R.id.btn_back);
        RelativeLayout account_info, change_password;

        account_info = findViewById(R.id.account_info);
        change_password = findViewById(R.id.change_password);

        button.setOnClickListener(v -> {
            finish();
        });

        account_info.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });

    }
}
