package com.dev.mohamed.partyphotos.fireBase;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by mohamed on 3/17/18.
 */

public class StorageUtilies {

    public static void getListOfLinks(ArrayList<Uri> listOfUri,OnImageUploaded onImageUploaded)
    {
        ArrayList<String> listOfLinks=new ArrayList<>();
        Iterator<Uri> iterator=listOfUri.iterator();



        while (iterator.hasNext())
        {

            listOfLinks.add(getImageDonloadUrl(iterator.next()));

            if (!iterator.hasNext())
                onImageUploaded.loaded(listOfLinks);

        }

    }


    public static String getImageDonloadUrl(Uri file) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
         StorageReference reference = storage.getReference();
        final String[] link = {""};


        if (file != null) {


            final StorageReference photoref = reference.child("photos").child(file.getLastPathSegment());

            final UploadTask uploadTask = photoref.putFile(file);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return photoref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        link[0] =downloadUri.toString();
                        Log.e("lol2",link[0]);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
            Log.e("lol",link[0]);
            return link[0];
        }

        return link[0];

    }




    public static void deleteImage(final String mImageUrl)
    {
        if (mImageUrl!=null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference photoRef = storage.getReferenceFromUrl(mImageUrl);

            photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });
        }
    }

    public interface OnImageUploaded
    {
        void loaded(ArrayList<String> urls);
    }
}
