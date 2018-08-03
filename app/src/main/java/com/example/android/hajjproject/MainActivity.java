package com.example.android.hajjproject;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import net.alhazmy13.gota.Gota;
import net.alhazmy13.gota.GotaResponse;
import net.alhazmy13.mediapicker.Image.ImagePicker;


import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Gota.OnRequestPermissionsBack {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                new Gota.Builder(MainActivity.this)
                        .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                        .requestId(1)
                        .setListener(MainActivity.this)
                        .check();
            }
        });

        ((Button)findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(MainActivity.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 123){
            Toast.makeText(this,"LAT"+data.getDoubleExtra("MAP_LAT",0),Toast.LENGTH_LONG).show();
            Toast.makeText(this,"LAT"+data.getDoubleExtra("MAP_LNG",0),Toast.LENGTH_LONG).show();
        }
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Log.d("TAG",mPaths.get(0));
            imageView.setImageBitmap(BitmapFactory.decodeFile(mPaths.get(0)));

///storage/emulated/0/mediapicker/images/715e15cf-2773-4b87-b711-65db3e53aba8.png
            //Your Code

            //Uri imageuri = Uri.fromFile(/storage/emulated/0/mediapicker/images/715e15cf-2773-4b87-b711-65db3e53aba8.png);


        }


    }

    @Override
    public void onRequestBack(int requestId, @NonNull GotaResponse gotaResponse) {
        if(gotaResponse.isAllGranted() && requestId == 1){
            startActivityForResult(new Intent(MainActivity.this,MapsActivity.class),123);//ait for resilt

        }

    }
     public void submit(View view){
         Intent intent = new Intent(Intent.ACTION_SENDTO);
         intent.setData(Uri.parse("mailto:")); // only email apps should handle thispng
         intent.putExtra(Intent.EXTRA_SUBJECT, "بلاغ");
         intent.putExtra(Intent.EXTRA_TEXT,"maha");
         

         if (intent.resolveActivity(getPackageManager()) != null) {
             startActivity(intent);
         }
     }









}
