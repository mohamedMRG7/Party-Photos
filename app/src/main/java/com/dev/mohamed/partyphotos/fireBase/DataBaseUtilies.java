package com.dev.mohamed.partyphotos.fireBase;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.dev.mohamed.partyphotos.data.PartyData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    public static final int CARD_ADDTION=1;
    private static final int OFFER_ADDITION=2;
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


    public static void acceptCard (String uId, final Context context)
    {
        Map value=new HashMap<>();
        value.put("isAccepted","true");
        FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD).child(uId).updateChildren(value)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(context, "done",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static void updateOffer (String uId, String offerImageLink , final Context context, final FragmentManager manager)
    {

        Map value=new HashMap<>();
        value.put("offerImg",offerImageLink);
        FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD).child(uId).updateChildren(value)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
                    }
                });

    }




    public static void getDataFromDb(final Context context, final onresiveData data)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child(CARDS_CHILD);


        ChildEventListener listener=new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                try{

                    if (!dataSnapshot.getKey().equals("zzzzzzzzzzzzz")){
                        data.resiveData(dataSnapshot.getValue(PartyData.class));
                        }
                    else data.lastReseved();

                }catch (Exception ignored){}



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                try {
                    data.resiveData(dataSnapshot.getValue(PartyData.class));

                }catch (Exception ignored){}



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                data.deleteUser(dataSnapshot.getValue(PartyData.class));
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


    public  interface onresiveData
    {
        void resiveData(PartyData data);
        void lastReseved();
        void deleteUser(PartyData data);
    }

}
