package com.dev.mohamed.partyphotos.localStorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.FutureTarget;


import java.util.concurrent.ExecutionException;

/**
 * Created by moham on 7/9/2018.
 */

public class DownloadPhotoAsynTask {


    public static void startLoad(Context context,Done done,String img)
    {
        new LoadInBackGround(context,done).execute(img);
    }


     static class  LoadInBackGround extends AsyncTask<String,Void,Bitmap>
    {

        Context context;
        Done done;
        public LoadInBackGround(Context context,Done done) {
            this.context = context;
            this.done=done;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;


                RequestBuilder<Bitmap> b= Glide.with(context).asBitmap().load(strings[0]);
                final FutureTarget<Bitmap> target=b.submit();
            try {
                bitmap=target.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            done.done(bitmap);

        }
    }

    public  interface  Done
    {
        void done(Bitmap bitmap);
    }
}
