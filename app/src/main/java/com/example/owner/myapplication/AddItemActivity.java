package com.example.owner.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        PropertyDatabase dbConnection=new PropertyDatabase(this);
        db= dbConnection.openDatabase();
        final EditText Address_input=findViewById(R.id.mAddress);
        final EditText Price_input=findViewById(R.id.mPrice);
        final EditText Bedroom_input=findViewById(R.id.mBedroom);
        Button button_create= findViewById(R.id.button_create);
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Property p=new Property();
                p.setmAdress(Address_input.getText().toString());
                p.setmPrice(Integer.parseInt(Price_input.getText().toString()));
                p.setmBedrooms(Integer.parseInt(Bedroom_input.getText().toString()));
                PropertyTable.insert(db,p);
                Toast.makeText(AddItemActivity.this,"create success",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddItemActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
