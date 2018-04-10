package com.example.owner.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BoughtListActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_list);
        PropertyDatabase dbConnection=new PropertyDatabase(this);
        db= dbConnection.openDatabase();
        ListView boughtList= findViewById(R.id.list_bought);
        final ArrayList<Property> properties = PropertyTable.selectAll_bought(db);
        final BoughtPropertyAdapter arrayAdapter=new BoughtPropertyAdapter(BoughtListActivity.this,R.layout.bought_item,properties, db);
        boughtList.setAdapter(arrayAdapter);
    }
}
