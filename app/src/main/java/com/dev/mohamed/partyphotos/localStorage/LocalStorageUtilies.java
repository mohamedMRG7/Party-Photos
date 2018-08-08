package com.dev.mohamed.partyphotos.localStorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import com.dev.mohamed.partyphotos.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by moham on 7/10/2018.
 */

public class LocalStorageUtilies {


    private static String downloadFolder ="Party Photos";
    private static File myDire=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+downloadFolder);

    public static void downloadImage(Bitmap bitmap, Context context)
    {

        final Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        final String fname = "image" + n + ".png";
        myDire.mkdirs();
        File image = new File(myDire, fname);

        FileOutputStream out ;

        try {
            out=new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.close();
            out.flush();
            Toast.makeText(context, R.string.done,Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Bitmap> getDownloadedPhotos()
    {
        List<Bitmap> list = new ArrayList<>();
        if (!myDire.exists()) {
                myDire.mkdir();
        }else {
            String[] photosNames = myDire.list();
            String path = myDire.getPath();
            for (int i = 0; i < myDire.list().length; i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + photosNames[i]);
                list.add(bitmap);
            }

        }
        return list;
    }
}
