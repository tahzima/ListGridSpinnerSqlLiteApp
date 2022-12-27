package com.example.applistvillesqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListeActivity extends AppCompatActivity {
    ListView list;
    Button btnAdd, btnEdit, btnDelete;
    EditText newCity, editedCity;
    ArrayAdapter arrayAdapter;
    DatabaseHelper db;
    ArrayList<String> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        this.setTitle("Liste des villes marocaines (ListView)");
        db=new DatabaseHelper(this);
        list = findViewById(R.id.list);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        listItem=new ArrayList<>();

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        viewData();
        /*** Ajouter une ville  ***/

        AlertDialog.Builder builderAdd = new AlertDialog.Builder(this);
        builderAdd.setTitle("Ajouter une ville");
        builderAdd.setMessage("Saisir la ville ici:");
        newCity = new EditText(this);
        builderAdd.setView(newCity);
        builderAdd.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String city = newCity.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vous n'avez pas encore saisi la ville!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted=db.insertData(city);
                    listItem.clear();
                    viewData();
                    if(isInserted == true)
                        Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),"Data not Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
        builderAdd.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialogAdd = builderAdd.create();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
                newCity.setText("");
            }
        });


        /*** Modifier une ville  ***/

        AlertDialog.Builder builderEdit = new AlertDialog.Builder(this);
        builderEdit.setTitle("Modifier une ville");
        builderEdit.setMessage("Modifier la ville ici:");
        editedCity = new EditText(this);
        builderEdit.setView(editedCity);
        builderEdit.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String cityEdited = editedCity.getText().toString();
                if (cityEdited.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vous ne pouvez pas avoir une ville vide!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean itemCheck = false;
                    String id="";
                    for (int v = 0; v < list.getCount(); v++) {
                        if (list.isItemChecked(v)) {
                            itemCheck=true;
                            id=db.getId(list.getItemAtPosition(v).toString());
                            boolean isUpdate = db.updateData(id,cityEdited);
                            if(isUpdate == true)
                                Toast.makeText(getApplicationContext(),"Data Update",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(),"Data not Updated",Toast.LENGTH_LONG).show();
                            itemCheck = true;
                        }
                    }
                    if (!itemCheck) {
                        Toast.makeText(getApplicationContext(), "Veuillez sélectionner une ville!", Toast.LENGTH_SHORT).show();
                    }
                    listItem.clear();
                    viewData();
                }
            }
        });
        builderEdit.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialogEdit = builderEdit.create();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int v = 0; v < list.getCount(); v++) {
                    if (list.isItemChecked(v)) {
                        editedCity.setText(list.getItemAtPosition(v).toString());
                    }
                }
                dialogEdit.show();
            }
        });


        /*** Supprimer une ville  ***/

        AlertDialog.Builder builderDelete = new AlertDialog.Builder(this);
        builderDelete.setTitle("Voulez-vous vraiment supprimer cette ville?");
        builderDelete.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean itemCheck = false;
                String id="";
                Integer deletedRows = null;

                for (int v = 0; v < list.getCount(); v++) {
                    if (list.isItemChecked(v)) {
                        id=db.getId(list.getItemAtPosition(v).toString());
                        deletedRows = db.deleteData(id);
                        itemCheck=true;
                        if(deletedRows > 0) {
                            Toast.makeText(getApplicationContext(), "is deleted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "is not deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                if (!itemCheck) {
                    Toast.makeText(getApplicationContext(), "Veuillez sélectionner une ville!", Toast.LENGTH_SHORT).show();
                }

                listItem.clear();
                viewData();
            }
        });
        builderDelete.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialogDelete = builderDelete.create();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete.show();
            }
        });

    }

    private void viewData() {
        Cursor cursor=db.getAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"no data inserted",Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listItem);
            list.setAdapter(arrayAdapter);
        }
    }
}