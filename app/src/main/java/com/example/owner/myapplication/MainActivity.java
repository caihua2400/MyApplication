package com.example.owner.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PropertyDatabase dbConnection=new PropertyDatabase(this);
        final SQLiteDatabase db= dbConnection.openDatabase();
        Log.d("createTable",PropertyTable.CREATE_STATEMENT);
        Property property1 = new Property();
        property1.setmAdress("742 Evergreen Terrace");
        property1.setmBedrooms(4);
        property1.setmPrice(250000);
        Property property2 = new Property();
        property2.setmAdress("4352 Wisteria Lane");
        property2.setmBedrooms(5);
        property2.setmPrice(500000);
        PropertyTable.insert(db, property1);
        PropertyTable.insert(db, property2);
        ArrayList<Property> properties = PropertyTable.selectAll(db);
        for (Property p: properties
             ) {
            Log.d("Mainactivity",p.getmAdress());
        }




        ListView listView=(ListView) findViewById(R.id.list_view);
        PropertyAdapter arrayAdapter=new PropertyAdapter(MainActivity.this,R.layout.property_item,properties);
        listView.setAdapter(arrayAdapter);

    }
}
