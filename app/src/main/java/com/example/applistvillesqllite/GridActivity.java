package com.example.applistvillesqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {
    GridView grid;
    DatabaseHelper myDb;
    ArrayList<String> villes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        myDb=new DatabaseHelper(this);
        this.setTitle("Liste des villes marocaines (GridView)");
        villes = new ArrayList<>();
        Cursor res = myDb.getAllData();
        while (res.moveToNext()) { villes.add( res.getString(1));};

        grid = findViewById(R.id.grid);
        grid.setAdapter(new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, villes));
    }
}