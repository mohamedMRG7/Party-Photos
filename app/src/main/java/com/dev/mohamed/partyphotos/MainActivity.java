package com.dev.mohamed.partyphotos;

import android.content.Intent;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.AutoCompleteTvAdapter;
import com.dev.mohamed.partyphotos.adapters.MostViewdRvAdapter;
import com.dev.mohamed.partyphotos.adapters.PartyPhotosAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    String [] names={"mohamed","mahmod","mosa","ahmed","a5dr"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView search=findViewById(R.id.et_search);
        RecyclerView recyclerView=findViewById(R.id.rv_photos);
        final MostViewdRvAdapter adapter=new MostViewdRvAdapter();
        GridLayoutManager layoutManager=new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        List list=new ArrayList<>(Arrays.asList(names));

        final AutoCompleteTvAdapter arrayAdapter=new AutoCompleteTvAdapter(this,list);
        search.setAdapter(arrayAdapter);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("hey",arrayAdapter.getItem(i)+"");
            }
        });

    }

    public void startParty(View view) {
        startActivity(new Intent(this,StartPartyActivity.class));
    }
}
