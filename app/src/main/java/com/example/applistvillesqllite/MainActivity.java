package com.example.applistvillesqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnListe,btnGrid,btnSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrid = findViewById(R.id.btnGrid);
        btnListe = findViewById(R.id.btnListe);
        btnSpinner = findViewById(R.id.btnSpinner);

        btnListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListe = new Intent(getApplicationContext(),ListeActivity.class);
                startActivity(intentListe);
            }
        });

        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSpinner = new Intent(getApplicationContext(),SpinnerActivity.class);
                startActivity(intentSpinner);
            }
        });

        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGrid = new Intent(getApplicationContext(),GridActivity.class);
                startActivity(intentGrid);
            }
        });

    }
}