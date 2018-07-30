package com.dev.mohamed.partyphotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.camera.CameraUtilies;
import com.dev.mohamed.partyphotos.data.PartyData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPartyActivity extends AppCompatActivity implements  SwitchCompat.OnCheckedChangeListener{


    @BindView(R.id.img_avatar) ImageView mAvatar;
    @BindView(R.id.sc_party_state)SwitchCompat mPartyState;
    @BindView(R.id.ed_partyname)EditText mPartyName;
    @BindView(R.id.ed_password)EditText mPassword;
    public static String PARTY_DATA_KEY="data";
    Bitmap mainPhotoBitmab;
    private String isPrivate="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_party);

        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.pplogocam).apply(new RequestOptions().circleCrop()).into(mAvatar);

        mPartyState.setOnCheckedChangeListener(this);
    }


    public void takeMainPhoto(View view) {

        CameraUtilies.takePicture(this);

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CameraUtilies.REQUEST_IMAGE_CAPTURE &&resultCode==RESULT_OK)
        {
            mainPhotoBitmab=(Bitmap) data.getExtras().get("data");

            Glide.with(this).load(mainPhotoBitmab).apply(new RequestOptions().circleCrop()).into(mAvatar);
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {
            mPassword.setVisibility(View.VISIBLE);
            isPrivate="true";
        }else
            {
                mPassword.setVisibility(View.GONE);
                isPrivate="false";
            }
    }

    public void openParty(View view) {
        String partyname=mPartyName.getText().toString();
        String partyPass=mPassword.getText().toString();
        PartyData data;
        Intent intent=new Intent(this,PartyManageActivity.class);
       if (isPrivate.equals("false"))
       {
           if (!partyname.isEmpty())
           {
               data=new PartyData(partyname,partyPass,isPrivate,mainPhotoBitmab);
               intent.putExtra(PARTY_DATA_KEY,data);
               startActivity(intent);
           }else
               {
                   mPartyName.setHintTextColor(Color.RED);
                   Toast.makeText(this,"Please enter the party name ..",Toast.LENGTH_LONG).show();
               }
       }

        else if (isPrivate.equals("true"))
       {
           if (!partyname.isEmpty() && !partyPass.isEmpty())
           {
               data=new PartyData(partyname,partyPass,isPrivate,mainPhotoBitmab);
               intent.putExtra(PARTY_DATA_KEY,data);
               startActivity(intent);
           }else
           {
               if (partyname.isEmpty()&& partyPass.isEmpty()){mPartyName.setHintTextColor(Color.RED);  mPassword.setHintTextColor(Color.RED);}
               else if (partyname.isEmpty())mPartyName.setHintTextColor(Color.RED); else mPassword.setHintTextColor(Color.RED);

               Toast.makeText(this,"Please enter the all fields ..",Toast.LENGTH_LONG).show();
           }
       }


    }
}
