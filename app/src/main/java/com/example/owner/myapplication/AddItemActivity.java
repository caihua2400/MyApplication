package com.example.owner.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_SHARE_IMAGE = 2;
    String mCurrentPhotoPath;
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
                p.setmPath(mCurrentPhotoPath);
                PropertyTable.insert(db,p);
                Toast.makeText(AddItemActivity.this,"create success",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddItemActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button button_take=findViewById(R.id.button_take_photo);
        button_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestToTakeAPicture();
            }
        });


    }
    //step 4
    private void requestToTakeAPicture()
    {
        ActivityCompat.requestPermissions(AddItemActivity.this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPTURE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay!
                    takeAPicture();
                } else {
                    // permission denied, boo!
                }
                return;

            case REQUEST_SHARE_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareLastImage();
                }
                return;
            }
        }
    }
    private void takeAPicture()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //takePictureIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);

        // Ensure that theres a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            try
            {
                File photoFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.owner.myapplication", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
            }
        }
    }
    //String mCurrentPhotoPath;
    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            //to get just the thumbnail:
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView myImageView = findViewById(R.id.myImageView);
            setPic(myImageView, mCurrentPhotoPath);
        }
    }
    private void setPic(ImageView myImageView, String path)
    {
        // Get the dimensions of the View
        int targetW = myImageView.getWidth();
        int targetH = myImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        myImageView.setImageBitmap(bitmap);


        //last bit, just for sharing example
        lastImage = bitmap;
    }
    Bitmap lastImage;
    private void requestToShareLastImage()
    {
        ActivityCompat.requestPermissions(AddItemActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_SHARE_IMAGE);
    }
    void shareLastImage()
    {
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), lastImage,"title", null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Share Via..."));


    }
}
