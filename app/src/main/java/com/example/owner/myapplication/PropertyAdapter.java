package com.example.owner.myapplication;

import android.app.Service;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Owner on 27/03/2018.
 */

public class PropertyAdapter extends ArrayAdapter<Property> {
    private int mLayoutResourceId;
    public PropertyAdapter(@NonNull Context context, int resource, @NonNull List<Property> objects) {
        super(context, resource, objects);
        this.mLayoutResourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row= layoutInflater.inflate(mLayoutResourceId,parent,false);
        Property property=getItem(position);
        TextView mAddress=(TextView) row.findViewById(R.id.textView);
        mAddress.setText(property.getmAdress());

        TextView mPrice=(TextView) row.findViewById(R.id.textView1);
        mPrice.setText(property.getmPrice()+"");

        TextView mBedrooms=(TextView) row.findViewById(R.id.textView2);
        mBedrooms.setText(property.getmBedrooms()+"");



        return row;
    }


}
