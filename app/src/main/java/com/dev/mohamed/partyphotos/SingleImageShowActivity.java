package com.dev.mohamed.partyphotos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.dev.mohamed.partyphotos.adapters.MostViewdRvAdapter;
import com.dev.mohamed.partyphotos.localStorage.DownloadPhotoAsynTask;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleImageShowActivity extends AppCompatActivity {
     Bitmap mBitmap;
     File myDire=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myparty");
     int i=6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_show);


       // Glide.with(this).load(MostViewdRvAdapter.images[6]).into((ImageView) findViewById(R.id.img_photo));


        DownloadPhotoAsynTask.startLoad(this, new DownloadPhotoAsynTask.Done() {
            @Override
            public void done(Bitmap bitmap) {

                ((ImageView) findViewById(R.id.img_photo)).setImageBitmap(bitmap);
                mBitmap=bitmap;
            }
        },MostViewdRvAdapter.images[6]);


        List list = new ArrayList();
        for (int i=0;i<myDire.list().length;i++)
        {
            Bitmap bitmap= BitmapFactory.decodeFile(myDire.getPath()+"/"+myDire.list()[i]);
            list.add(bitmap);
        }

        Log.e("list",list.size()+"");

       /* new Thread(new Runnable() {
            @Override
            public void run() {

                    RequestBuilder<Bitmap> b= Glide.with(getApplicationContext()).asBitmap().load(MostViewdRvAdapter.images[0]);
                    final FutureTarget <Bitmap> target=b.submit();
                try {
                     bitmap=target.get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        }).start();*/


    }

    private void savePhoto(Bitmap bitmap)
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
            Toast.makeText(this,"done",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadImage(View view) {

        savePhoto(mBitmap);

        DownloadPhotoAsynTask.startLoad(this, new DownloadPhotoAsynTask.Done() {
            @Override
            public void done(Bitmap bitmap) {

                ((ImageView) findViewById(R.id.img_photo)).setImageBitmap(bitmap);
                mBitmap=bitmap;
            }
        },MostViewdRvAdapter.images[i]);
        i++;
    }
}
