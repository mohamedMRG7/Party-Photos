package com.dev.mohamed.partyphotos;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.adapters.AutoCompleteTvAdapter;
import com.dev.mohamed.partyphotos.adapters.MostViewdRvAdapter;
import com.dev.mohamed.partyphotos.connection.CheckConnection;
import com.dev.mohamed.partyphotos.data.PartyData;
import com.dev.mohamed.partyphotos.fireBase.DataBaseUtilies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DataBaseUtilies.OnResiveData,MostViewdRvAdapter.OnPartyClick{



    private final String PARTY_LIST_KEY="partyListKey";
    public static final String OPEN_PARTY_KEY="openPartyKey";
    ArrayList<PartyData> partiesDataList;
    ArrayList<PartyData> openPartiesDataList;
    MostViewdRvAdapter adapter;
     AutoCompleteTvAdapter autoCompleteTvAdapter;
    AlertDialog b;
    AlertDialog.Builder dialogBuilder;
    View dialogView;
    PartyData partyData;

     @BindView(R.id.tv_party_name)TextView tvPartyName;
     @BindView(R.id.img_avatar)ImageView imgAvatar;
     @BindView(R.id.ed_password)EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        partiesDataList =new ArrayList<>();
        openPartiesDataList=new ArrayList<>();
        if (savedInstanceState!=null) {
            partiesDataList = savedInstanceState.getParcelableArrayList(PARTY_LIST_KEY);

        }

        final AutoCompleteTextView search=findViewById(R.id.et_search);
        RecyclerView recyclerView=findViewById(R.id.rv_photos);
        adapter=new MostViewdRvAdapter(this);
        adapter.updatePartiesList(openPartiesDataList);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        if (!CheckConnection.isOnline(this))
            Toast.makeText(this, R.string.no_connection_message,Toast.LENGTH_LONG).show();



        autoCompleteTvAdapter=new AutoCompleteTvAdapter(this, partiesDataList);
        search.setAdapter(autoCompleteTvAdapter);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PartyData data=autoCompleteTvAdapter.getItem(i);
                ImageView img=adapterView.findViewById(R.id.o3);
                search.setText(data.getPartyName());


                if (data.isClosedParty()) {
                    partyData = data;
                    b.show();
                    tvPartyName.setText(data.getPartyName());
                    Glide.with(MainActivity.this).load(data.getListOfPhotosLinks().get(0)).apply(new RequestOptions().circleCrop()).into(imgAvatar);
                }else openParty(data,img);
            }
        });

        DataBaseUtilies.getDataFromDb(this,this);
        initProgressDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARTY_LIST_KEY, partiesDataList);
    }

    public void startParty(View view) {
        startActivity(new Intent(this,StartPartyActivity.class));
    }


    @Override
    public void resiveData(PartyData data) {
        partiesDataList.add(data);


        if (!data.isClosedParty()) {
            openPartiesDataList.add(data);
            adapter.updatePartiesList(openPartiesDataList);
        }

        autoCompleteTvAdapter.updateData(partiesDataList);
    }

    @Override
    public void updateValue(PartyData data) {

        if (data.isClosedParty())
        {
            updateListValue(data,partiesDataList);
            autoCompleteTvAdapter.updateData(partiesDataList);

        }else {
            updateListValue(data,openPartiesDataList);
            adapter.updatePartiesList(openPartiesDataList);
        }

    }


    private void updateListValue(PartyData data,ArrayList<PartyData> partyData)
    {
        for (int i=0;i<partyData.size();i++)
        {
            if (partyData.get(i).getPartyName().equals(data.getPartyName())) {
                partyData.remove(i);
                partyData.add(i,data);
                break;
            }
        }
    }
    @Override
    public void partyClick(PartyData data,ImageView avatar) {

        DataBaseUtilies.addView(data.getPartyName(),data.getNumOfViews()+1);
        openParty(data,avatar);
    }

    public void initProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        dialogView = inflater.inflate(R.layout.party_auth_dialoge_layout, null);

        ButterKnife.bind(this,dialogView);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setTitle(R.string.party_pass_request);
        dialogBuilder.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String pass=edPassword.getText().toString();

                if (pass.equals(partyData.getPartyPassword())) {
                    openParty(partyData,imgAvatar);
                    DataBaseUtilies.addView(partyData.getPartyName(), partyData.getNumOfViews() + 1);
                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.wrong_pass), Toast.LENGTH_LONG).show();
                    edPassword.setText("");

                }
            }
        });

        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        b = dialogBuilder.create();

    }

    private void openParty(PartyData partyData,ImageView imgAvatar)
    {
        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        Bundle bundle=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair<View,String> pair = new Pair<View, String>(imgAvatar,imgAvatar.getTransitionName());
            bundle= ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair).toBundle();

        }
        intent.putExtra(OPEN_PARTY_KEY,partyData);
        startActivity(intent,bundle);
    }


}
