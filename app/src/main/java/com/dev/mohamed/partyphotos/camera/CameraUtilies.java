package com.dev.mohamed.partyphotos.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import static java.security.AccessController.getContext;

/**
 * Created by moham on 7/19/2018.
 */

public class CameraUtilies {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static void takePicture(Activity context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
           context.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
}
