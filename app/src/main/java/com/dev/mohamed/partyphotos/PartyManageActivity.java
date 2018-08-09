package com.dev.mohamed.partyphotos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.PartyManageAdapter;
import com.dev.mohamed.partyphotos.camera.CameraUtilies;
import com.dev.mohamed.partyphotos.connection.CheckConnection;
import com.dev.mohamed.partyphotos.data.PartyData;
import com.dev.mohamed.partyphotos.fireBase.DataBaseUtilies;
import com.dev.mohamed.partyphotos.fireBase.StorageUtilies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartyManageActivity extends AppCompatActivity implements StorageUtilies.OnImageUploaded{

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    ArrayList<Uri> mPhotosList;
    ArrayList<String>mPhotosLinkList;
    @BindView(R.id.rv_party_manage)RecyclerView rvPartyPhotosList;
    @BindView(R.id.tv_party_name)TextView tvPartyname;
    @BindView(R.id.toolbar)Toolbar toolbar;
    PartyManageAdapter adapter;
    private boolean isFirstStart=true;
    private final String First_START_KEY ="firstStart";
    private final String PHOTOS_LIST_KEY="photosList";
    private final String PHOTOS_LINKS_KEY="photosLinks";
    TextView mUploaded;
    PartyData data;
    AlertDialog b;
    AlertDialog.Builder dialogBuilder;
    View dialogView;
    TextView mTotalPhotos;
    private boolean haveMainPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_manage);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        mPhotosList=new ArrayList<>();
        mPhotosLinkList=new ArrayList<>();

        data= (PartyData) getIntent().getExtras().get(StartPartyActivity.PARTY_DATA_KEY);
        tvPartyname.setText(data.getPartyName());
        if (data.getPartyMainPhotoUri()!=null)
            Glide.with(this).load(data.getPartyMainPhotoUri()).apply(new RequestOptions().circleCrop()).into((ImageView) findViewById(R.id.img_avatar));
        else
            Glide.with(this).load(R.drawable.pplogo).apply(new RequestOptions().circleCrop()).into((ImageView) findViewById(R.id.img_avatar));

        haveMainPhoto=data.getPartyMainPhotoUri()!=null;
        adapter=new PartyManageAdapter();
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        rvPartyPhotosList.setLayoutManager(layoutManager);
        rvPartyPhotosList.setAdapter(adapter);
        if (savedInstanceState!=null)
        {
            isFirstStart=savedInstanceState.getBoolean(First_START_KEY);
            mPhotosList=savedInstanceState.getParcelableArrayList(PHOTOS_LIST_KEY);
            mPhotosLinkList=savedInstanceState.getStringArrayList(PHOTOS_LINKS_KEY);
            adapter.updateListImages(mPhotosList);
        }
        if (isFirstStart) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {


                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }

                }
            }else CameraUtilies.takePicture(this);

            isFirstStart=false;
        }

        initProgressDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    CameraUtilies.takePicture(this);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(First_START_KEY,isFirstStart);
        outState.putParcelableArrayList(PHOTOS_LIST_KEY, mPhotosList);
        outState.putStringArrayList(PHOTOS_LINKS_KEY,mPhotosLinkList);
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

        if (CheckConnection.isOnline(this)) {
            for (int i = 0; i < 2; i++) {

                if (haveMainPhoto && i == 0)
                    uploadeMainPhoto();

                if (i == 1)
                    uploadeListOfPhotos();

                if (i == 0)
                    b.show();
            }
            if (haveMainPhoto)
                mTotalPhotos.setText(String.valueOf(mPhotosList.size() + 1));
            else mTotalPhotos.setText(String.valueOf(mPhotosList.size()));
        }
        else Toast.makeText(this, R.string.no_connection_message,Toast.LENGTH_LONG).show();
       }


        private void uploadeListOfPhotos()
        {
            for (int i=0;i<mPhotosList.size();i++) {


                StorageUtilies.getImageDonloadUrl(mPhotosList.get(i), this);

            }

        }

        private void uploadeMainPhoto()
        {
            StorageUtilies.getImageDonloadUrl(data.getPartyMainPhotoUri(), this);
        }


    @Override
    public void loaded(String links, int i) {

        if (data.getPartyMainPhotoUri()!=null )
        {
            data.setPartyMainPhotoLink(links);
            data.setPartyMainPhotoUri(null);
        }

        mPhotosLinkList.add(links);
        data.setListOfPhotosLinks(mPhotosLinkList);
        data.setNumOfPhotos(mPhotosLinkList.size());
        DataBaseUtilies.insert(data,this);
        mUploaded.setText(String.valueOf(i));

        if (haveMainPhoto){

        if (i==mPhotosList.size()+1)
        {
            startActivity(new Intent(this,MainActivity.class));
            Toast.makeText(this, R.string.cool_party,Toast.LENGTH_LONG).show();
            StorageUtilies.resiteCount();
            finish();
        }

        }else

        if (i==mPhotosList.size())
        {
            startActivity(new Intent(this,MainActivity.class));
            Toast.makeText(this,R.string.cool_party,Toast.LENGTH_LONG).show();
            StorageUtilies.resiteCount();
            finish();
        }

    }


    public void initProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(PartyManageActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        dialogView = inflater.inflate(R.layout.dialge_layout, null);
        mUploaded =dialogView.findViewById(R.id.tvcount);
        mTotalPhotos =dialogView.findViewById(R.id.tv_totalPhotos);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setTitle(R.string.please_wait);

        b = dialogBuilder.create();

    }


}








