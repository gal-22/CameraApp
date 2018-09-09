package com.zaid.green.cameraapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Constants
    final int REQUEST_CAMERA_PERMISSION = 200;

    // Objects
    private Bitmap imageBitmap;
    private Bitmap O;
    // UI objects
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        initViews();
        initBitmapFromImage();
        changeBitmap();
    }

    public void requestPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },REQUEST_CAMERA_PERMISSION);
            return;
        }
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initBitmapFromImage() {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        imageBitmap = drawable.getBitmap();
    }
    private void changeBitmap() {
        O = Bitmap.createBitmap(imageBitmap.getWidth(),imageBitmap.getHeight(), imageBitmap.getConfig());

        for(int i=0; i<imageBitmap.getWidth(); i++){
            for(int j=0; j < imageBitmap.getHeight(); j++){
                int pixel = imageBitmap.getPixel(i, j);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);


                O.setPixel(i, j, Color.argb(Color.alpha(pixel), redValue + 10, greenValue - 5, blueValue + 40));
            }
        }
        imageView.setImageBitmap(O);
    }
}
