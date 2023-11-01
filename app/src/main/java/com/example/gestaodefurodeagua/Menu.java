package com.example.gestaodefurodeagua;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    TextView username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headerfile);

        username = findViewById(R.id.username);
    }

    public Menu(String username) {
        this.username.setText(username);
    }
}
