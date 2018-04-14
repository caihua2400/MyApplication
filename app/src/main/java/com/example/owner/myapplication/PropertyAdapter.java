package com.example.owner.myapplication;

import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Owner on 27/03/2018.
 */

public class PropertyAdapter extends ArrayAdapter<Property> {
    private int mLayoutResourceId;
    private SQLiteDatabase databaseHandler;
    private PropertyAdapter propertyAdapter;

    public PropertyAdapter(@NonNull Context context, int resource, @NonNull List<Property> objects, SQLiteDatabase databaseHandler) {
        super(context, resource, objects);
        this.mLayoutResourceId=resource;
        this.databaseHandler=databaseHandler;

    }
    public void setAdapter(PropertyAdapter  pAdapter){
        this.propertyAdapter=pAdapter;

    }
    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row= layoutInflater.inflate(mLayoutResourceId,parent,false);
        final Property property=getItem(position);
        TextView mAddress=(TextView) row.findViewById(R.id.textView);
        mAddress.setText(property.getmAdress());

        TextView mPrice=(TextView) row.findViewById(R.id.textView1);
        mPrice.setText(property.getmPrice()+"");
        ImageView imageView= (ImageView) row.findViewById(R.id.image);

        setPic(imageView,property.getmPath());

        TextView mBedrooms=(TextView) row.findViewById(R.id.textView2);
        mBedrooms.setText(property.getmBedrooms()+"");
        Button button=row.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PropertyAdapter","Button Clicked");
                PropertyTable.delete(databaseHandler,property);
                PropertyTable.insert_bought(databaseHandler,property);
                remove(property);


                propertyAdapter.notifyDataSetChanged();
            }
        });





        return row;
    }

    private void setPic(ImageView myImageView, String path)
    {
        // Get the dimensions of the View
        int targetW = myImageView.getWidth();
        int targetH = myImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
     //   bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        myImageView.setImageBitmap(bitmap);


        //last bit, just for sharing example
        lastImage = bitmap;
    }

    Bitmap lastImage;


}
