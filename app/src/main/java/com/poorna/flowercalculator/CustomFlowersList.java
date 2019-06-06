package com.poorna.flowercalculator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomFlowersList extends AppCompatActivity   {


    Button btnSave,btnList;
    ArrayList<Flowers> flowerslist;
    SQLiteDataBaseClass database;
    ListView listView;
    TextView note;
    String value;


    Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_list);

        database = new SQLiteDataBaseClass(this);
        listView = (ListView) findViewById(android.R.id.list);
        flowerslist = (ArrayList<Flowers>) database.getAllFlowers();

        FlowerListAdapter flowerListAdapter = new FlowerListAdapter(CustomFlowersList.this, R.layout.row, flowerslist,database);
        //adding the adapter to listview

        note=findViewById(R.id.note);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("sample_name");

            note.setText(value);
        }

        listView.setAdapter(flowerListAdapter);



    }











}
