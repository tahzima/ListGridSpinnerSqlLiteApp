package com.example.applistvillesqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity {
    Spinner spinner;
    DatabaseHelper db;
    ArrayList<String> villes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        db=new DatabaseHelper(this);
        this.setTitle("Liste des villes marocaines (Spinner)");
        spinner = findViewById(R.id.spinner);
        Cursor res = db.getAllData();
        villes=new ArrayList<>();
        while (res.moveToNext()) { villes.add( res.getString(1));};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, villes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}