package com.example.owner.myapplication;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        /*Property property1 = new Property();
        property1.setmAdress("742 Evergreen Terrace");
        property1.setmBedrooms(4);
        property1.setmPrice(250000);
        Property property2 = new Property();
        property2.setmAdress("4352 Wisteria Lane");
        property2.setmBedrooms(5);
        property2.setmPrice(500000);
        PropertyTable.insert(db, property1);
        PropertyTable.insert(db, property2);*/
        final ArrayList<Property> properties = PropertyTable.selectAll(db);
        for (Property p: properties
             ) {
            Log.d("Mainactivity",p.getmAdress());
        }




        ListView listView=(ListView) findViewById(R.id.list_view);
        final PropertyAdapter arrayAdapter=new PropertyAdapter(MainActivity.this,R.layout.property_item,properties);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "item click");
                final Property p=properties.get(i);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update");
                final EditText editText=new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setText(p.getmAdress());
                builder.setView(editText);
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        p.setmAdress(editText.getText().toString());
                        PropertyTable.update(db,p);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

    }
}
