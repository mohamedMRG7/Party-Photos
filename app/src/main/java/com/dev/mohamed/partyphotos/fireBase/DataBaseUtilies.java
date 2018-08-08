package com.dev.mohamed.partyphotos.fireBase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.dev.mohamed.partyphotos.data.PartyData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 3/20/18.
 */

public class DataBaseUtilies {


    private static final String CARDS_CHILD="parties";

    public static void insert(PartyData data, final Activity context)
    {


        FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD).child(data.getPartyName())
                .setValue(data)

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "error",Toast.LENGTH_SHORT).show();
                       context.finish();

                    }
                })

                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }



    public static void addView (String partyName, int newNumViews)
    {

        Map value=new HashMap<>();
        value.put("numOfViews",newNumViews);
        FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD).child(partyName).updateChildren(value);

    }




    public static void getDataFromDb(final Context context, final OnResiveData data)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD);


        ChildEventListener listener=new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        data.resiveData(dataSnapshot.getValue(PartyData.class));


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    data.updateValue(dataSnapshot.getValue(PartyData.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        reference.addChildEventListener(listener);

    }


    public  interface OnResiveData
    {
        void resiveData(PartyData data);
        void updateValue(PartyData data);

    }

}
