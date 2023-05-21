package com.example.sharebitmapimagetelegramwhatsapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                PackageManager.PERMISSION_GRANTED);

        imageView = findViewById(R.id.imageView);
    }

    public void buttonShareBitmap(View view){
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal memory
        File fileImage = new File(storageVolume.getDirectory().getPath() + "/Download/images.jpeg");


        Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getPath());
        imageView.setImageBitmap(bitmap);
        String stringPath = MediaStore.Images.Media.insertImage(this.getContentResolver(),
                bitmap,"Shared Image", null);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(stringPath));

        startActivity(Intent.createChooser(intent, "Share the Image ... "));
    }
}