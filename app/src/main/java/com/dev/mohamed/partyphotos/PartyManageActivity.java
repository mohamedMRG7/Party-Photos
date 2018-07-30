package com.dev.mohamed.partyphotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.PartyManageAdapter;
import com.dev.mohamed.partyphotos.camera.CameraUtilies;
import com.dev.mohamed.partyphotos.data.PartyData;
import com.dev.mohamed.partyphotos.fireBase.DataBaseUtilies;
import com.dev.mohamed.partyphotos.fireBase.StorageUtilies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartyManageActivity extends AppCompatActivity implements StorageUtilies.OnImageUploaded{

    ArrayList<Uri> mPhotosList;
    ArrayList<String>mPhotosLinkList;
    @BindView(R.id.rv_party_manage)RecyclerView rvPartyPhotosList;
    PartyManageAdapter adapter;
    private boolean isFirstStart=true;
    private final String First_START_KEY ="firstStart";
    private final String PHOTOS_LIST_KEY="photosList";
    private static final String DONE="done";
    PartyData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manage);

        ButterKnife.bind(this);



        mPhotosList=new ArrayList<>();
        mPhotosLinkList=new ArrayList<>();
        data= (PartyData) getIntent().getExtras().get(StartPartyActivity.PARTY_DATA_KEY);
        if (data.getPartyMainPhotoBitmab()!=null)
            Glide.with(this).load(data.getPartyMainPhotoBitmab()).apply(new RequestOptions().circleCrop()).into((ImageView) findViewById(R.id.img_avatar));
        else
            Glide.with(this).load(R.drawable.pplogocam).apply(new RequestOptions().circleCrop()).into((ImageView) findViewById(R.id.img_avatar));


        adapter=new PartyManageAdapter();
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        rvPartyPhotosList.setLayoutManager(layoutManager);
        rvPartyPhotosList.setAdapter(adapter);
        if (savedInstanceState!=null)
        {
            isFirstStart=savedInstanceState.getBoolean(First_START_KEY);
            mPhotosList=savedInstanceState.getParcelableArrayList(PHOTOS_LIST_KEY);
            adapter.updateListImages(mPhotosList);
        }
        if (isFirstStart) {

            CameraUtilies.takePicture(this);
            isFirstStart=false;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(First_START_KEY,isFirstStart);
        outState.putParcelableArrayList(PHOTOS_LIST_KEY, mPhotosList);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CameraUtilies.REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            mPhotosList.add(uri);
            adapter.updateListImages(mPhotosList);
            CameraUtilies.takePicture(this);
        }
    }

    public void takePhoto(View view) {


        CameraUtilies.takePicture(this);
    }




    public void addPartyToDb(View view) {

            StorageUtilies.getListOfLinks(mPhotosList,this);

       }

    @Override
    public void loaded(ArrayList<String> urls) {
        data.setListOfPhotosLinks(urls);
        DataBaseUtilies.insert(data,this);

    }
}








