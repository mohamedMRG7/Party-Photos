package com.dev.mohamed.partyphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.MostViewdRvAdapter;
import com.dev.mohamed.partyphotos.adapters.PartyPhotosAdapter;

public class DetailsActivity extends AppCompatActivity {

    ImageView bgPhoto,avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis);

        bgPhoto=findViewById(R.id.img_bg_photo);
        avatar=findViewById(R.id.img_avatar);

        Glide.with(this).load(MostViewdRvAdapter.images[0]).into(bgPhoto);
        Glide.with(this).load(MostViewdRvAdapter.images[1]).apply(new RequestOptions().circleCrop()).into(avatar);


        RecyclerView rvPartyPhotos=findViewById(R.id.rv_partyphotos);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        PartyPhotosAdapter partyPhotosAdapter=new PartyPhotosAdapter();
        rvPartyPhotos.setLayoutManager(staggeredGridLayoutManager);
        rvPartyPhotos.setHasFixedSize(true);
        rvPartyPhotos.setAdapter(partyPhotosAdapter);
    }
}
