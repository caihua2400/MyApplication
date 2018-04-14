package com.example.owner.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Owner on 21/03/2018.
 */

public class PropertyTable {
    public static final String TABLE_NAME="property";
    public static final String KEY_PROPERTY_ID="property_id";
    public static final String KEY_ADDRESS="address";
    public static final String KEY_PRICE="price";
    public static final String KEY_BEDROOMS="bedrooms";
    public static final String TABLE_NAME_BOUGHT="property_bought";
    public static final String KEY_ITEM_PHOTO_PATH="item_path";
    public static final String CREATE_STATEMENT="CREATE TABLE"
            +" "
            +TABLE_NAME
            + " (" + KEY_PROPERTY_ID + " integer primary key autoincrement, "
            + KEY_ADDRESS + " string not null, "
            + KEY_PRICE + " int not null, "
            + KEY_BEDROOMS + " string not null, "
            + KEY_ITEM_PHOTO_PATH+ " string not null "
            +");";
    public static final String CREATE_STATEMENT_BOUGHT="CREATE TABLE"
            +" "
            +TABLE_NAME_BOUGHT
            + " (" + KEY_PROPERTY_ID + " integer primary key autoincrement, "
            + KEY_ADDRESS + " string not null, "
            + KEY_PRICE + " int not null, "
            + KEY_BEDROOMS + " string not null, "
            + KEY_ITEM_PHOTO_PATH+ " string not null"
            +");";
    public static void insert(SQLiteDatabase db,Property p){
        ContentValues values=new ContentValues();
        values.put(KEY_ADDRESS,p.getmAdress());
        values.put(KEY_PRICE,p.getmPrice());
        values.put(KEY_BEDROOMS,p.getmBedrooms());
        values.put(KEY_ITEM_PHOTO_PATH,p.getmPath());
        db.insert(TABLE_NAME,null,values);
    }
    public static void insert_bought(SQLiteDatabase db,Property p){
        ContentValues values=new ContentValues();
        values.put(KEY_ADDRESS,p.getmAdress());
        values.put(KEY_PRICE,p.getmPrice());
        values.put(KEY_BEDROOMS,p.getmBedrooms());
        values.put(KEY_ITEM_PHOTO_PATH,p.getmPath());
        db.insert(TABLE_NAME_BOUGHT,null,values);
    }
    public static void update(SQLiteDatabase db,Property p){
        ContentValues values=new ContentValues();
        values.put(KEY_ADDRESS,p.getmAdress());
        values.put(KEY_PRICE,p.getmPrice());
        values.put(KEY_BEDROOMS,p.getmBedrooms());
        values.put(KEY_ITEM_PHOTO_PATH,p.getmPath());
        db.update(TABLE_NAME,values,KEY_PROPERTY_ID+"= ?",new String[]{""+p.getmPropertyID()});

    }
    public static void delete(SQLiteDatabase db, Property p){
            db.delete(TABLE_NAME,KEY_PROPERTY_ID+"= ?",new String[]{""+p.getmPropertyID()});
            //arrayAdapter.notifyDataSetChanged();
    }
    public static ArrayList<Property> selectAll(SQLiteDatabase db){
        ArrayList<Property> results=new ArrayList<Property>();
        Cursor c= db.query(TABLE_NAME,null,null,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            while(!c.isAfterLast()){
                Property p=createFromCursor(c);
                results.add(p);
                c.moveToNext();
            }
        }
        return results;
    }
    public static ArrayList<Property> selectAll_bought(SQLiteDatabase db){
        ArrayList<Property> results=new ArrayList<Property>();
        Cursor c= db.query(TABLE_NAME_BOUGHT,null,null,null,null,null,null);
        if(c!=null){
            c.moveToNext();
            while(!c.isAfterLast()){
                Property p=createFromCursor(c);
                results.add(p);
                c.moveToNext();
            }
        }
        return results;
    }

    public static Property getPropertyById(SQLiteDatabase db,int id){
        Cursor c=db.query(TABLE_NAME,null,KEY_PROPERTY_ID+"="+id,null,null,null,null);
        return createFromCursor(c);
    }

    private static Property createFromCursor(Cursor c) {
        if(c==null || c.isAfterLast() || c.isBeforeFirst()){
            return null;
        }else{
            Property p=new Property();
            p.setmPropertyID(c.getInt(c.getColumnIndex(KEY_PROPERTY_ID)));
            p.setmAdress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
            p.setmPrice(c.getInt(c.getColumnIndex(KEY_PRICE)));
            p.setmBedrooms(c.getInt(c.getColumnIndex(KEY_BEDROOMS)));
            p.setmPath(c.getString(c.getColumnIndex(KEY_ITEM_PHOTO_PATH)));
            return p;

        }
    }


}
