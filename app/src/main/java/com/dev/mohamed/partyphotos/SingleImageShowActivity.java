package com.dev.mohamed.partyphotos;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.dev.mohamed.partyphotos.adapters.MostViewdRvAdapter;
import com.dev.mohamed.partyphotos.adapters.PartyPhotosAdapter;
import com.dev.mohamed.partyphotos.localStorage.DownloadPhotoAsynTask;
import com.dev.mohamed.partyphotos.localStorage.LocalStorageUtilies;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleImageShowActivity extends AppCompatActivity {



     Bitmap mBitmap;

     @BindView(R.id.img_photo)ImageSwitcher imageSwitcher;
     @BindView(R.id.img_right)ImageView imgRight;
     @BindView(R.id.img_left)ImageView imgLeft;
     ArrayList<String> listOfPhotosLinks;

     int photoNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_show);

        ButterKnife.bind(this);

        listOfPhotosLinks=getIntent().getStringArrayListExtra(PartyPhotosAdapter.PARTY_LIST_KEY);
        photoNum=getIntent().getIntExtra(PartyPhotosAdapter.PARTY_PHOTO_COUNT_KEY,0);



       // Glide.with(this).load(MostViewdRvAdapter.images[6]).into((ImageView) findViewById(R.id.img_photo));

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });



        showPhoto(Arrow.INIT);


        setArrowVisibility(Arrow.INIT);




    }



    public void downloadImage(View view) {


                LocalStorageUtilies.downloadImage(mBitmap,SingleImageShowActivity.this);

    }

    private void setArrowVisibility(Arrow arrowVisibility)
    {

        imgLeft.setVisibility(View.VISIBLE);
        imgRight.setVisibility(View.VISIBLE);
        if (arrowVisibility==Arrow.LEFT) {
            if (photoNum - 1 == 0)
                imgLeft.setVisibility(View.GONE);

        }
        if (arrowVisibility==Arrow.Right) {
            if (photoNum + 1 == listOfPhotosLinks.size() - 1)
                imgRight.setVisibility(View.GONE);

        }

        if (arrowVisibility==Arrow.INIT)
            if (photoNum==0)
                imgLeft.setVisibility(View.GONE);
            else if (photoNum==listOfPhotosLinks.size()-1)
                imgRight.setVisibility(View.GONE);
    }

    public void nextPhoto(View view) {

        showPhoto(Arrow.Right);
    }

    public void previousPhoto(View view) {
      showPhoto(Arrow.LEFT);
    }

    private void showPhoto(Arrow arrow)
    {
        switch (arrow)
        {
            case LEFT:
                DownloadPhotoAsynTask.startLoad(this, new DownloadPhotoAsynTask.Done() {
                    @Override
                    public void done(Bitmap bitmap) {

                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        imageSwitcher.setImageDrawable(drawable);
                        mBitmap=bitmap;
                    }
                },listOfPhotosLinks.get(photoNum-1));
                setArrowVisibility(Arrow.LEFT);
                photoNum--;

                break;
            case Right:
                DownloadPhotoAsynTask.startLoad(this, new DownloadPhotoAsynTask.Done() {
                    @Override
                    public void done(Bitmap bitmap) {


                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        imageSwitcher.setImageDrawable(drawable);
                        mBitmap=bitmap;
                    }
                },listOfPhotosLinks.get(photoNum+1));
                setArrowVisibility(Arrow.Right);
                photoNum++;
                break;
            case INIT:
                DownloadPhotoAsynTask.startLoad(this, new DownloadPhotoAsynTask.Done() {
                    @Override
                    public void done(Bitmap bitmap) {

                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        imageSwitcher.setImageDrawable(drawable);
                        mBitmap=bitmap;
                    }
                },listOfPhotosLinks.get(photoNum));
        }
    }

    private   enum Arrow
    {
        LEFT,Right,INIT
    }
}
