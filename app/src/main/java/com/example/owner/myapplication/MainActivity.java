package com.example.owner.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
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

    public static SQLiteDatabase db;
   // public static PropertyAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PropertyDatabase dbConnection=new PropertyDatabase(this);
        db= dbConnection.openDatabase();

        final ArrayList<Property> properties = PropertyTable.selectAll(db);
        Button buttonAdd=findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(intent);
            }
        });
        Button button_turnTo_bought= findViewById(R.id.button_turnTo_bought);
        button_turnTo_bought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2=new Intent(MainActivity.this,BoughtListActivity.class);
                startActivity(intent_2);
            }
        });



        ListView listView=(ListView) findViewById(R.id.list_view);
        final PropertyAdapter arrayAdapter=new PropertyAdapter(MainActivity.this,R.layout.property_item,properties, db);
        arrayAdapter.setAdapter(arrayAdapter);
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
