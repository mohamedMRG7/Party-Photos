package com.dev.mohamed.partyphotos;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.PartyPhotosAdapter;
import com.dev.mohamed.partyphotos.data.PartyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.img_bg_photo)ImageView bgPhoto;
    @BindView(R.id.img_avatar)ImageView avatar;
    @BindView(R.id.tv_viewsnum)TextView tvNumOfViews;
    @BindView(R.id.tv_photosnum)TextView tvNumOfPhotos;
    @BindView(R.id.tv_party_name)TextView tvPartyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_detalis);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode());
        }
        ButterKnife.bind(this);

        PartyData partyData= (PartyData) getIntent().getExtras().get(MainActivity.OPEN_PARTY_KEY);

        ArrayList<String>listOfPhotos=partyData.getListOfPhotosLinks();
        String backGroundPhotoLink;
        if (listOfPhotos.size() > 1)
            backGroundPhotoLink=listOfPhotos.get(1);
        else
            backGroundPhotoLink=listOfPhotos.get(0);


        String mainPhotoLink=partyData.getPartyMainPhotoLink();
        String numOfPhotos=String.valueOf(partyData.getNumOfPhotos());
        String numOfViews=String.valueOf(partyData.getNumOfViews());
        String partyName=partyData.getPartyName();
        Glide.with(this).load(backGroundPhotoLink).into(bgPhoto);
        if (mainPhotoLink!=null &&!mainPhotoLink.isEmpty())
        Glide.with(this).load(mainPhotoLink).apply(new RequestOptions().circleCrop()).into(avatar);
        else Glide.with(this).load(listOfPhotos.get(0)).apply(new RequestOptions().circleCrop()).into(avatar);

        tvNumOfPhotos.setText(numOfPhotos);
        tvNumOfViews.setText(numOfViews);
        tvPartyName.setText(partyName);
        RecyclerView rvPartyPhotos=findViewById(R.id.rv_partyphotos);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        PartyPhotosAdapter partyPhotosAdapter=new PartyPhotosAdapter();
        partyPhotosAdapter.updateListOfPhotos(listOfPhotos);
        rvPartyPhotos.setLayoutManager(staggeredGridLayoutManager);
        rvPartyPhotos.setHasFixedSize(true);
        rvPartyPhotos.setAdapter(partyPhotosAdapter);
    }
}
