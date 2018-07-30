package com.dev.mohamed.partyphotos.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dev.mohamed.partyphotos.R;
import com.dev.mohamed.partyphotos.localStorage.LocalStorageUtilies;


import java.util.List;

/**
 * Created by moham on 7/8/2018.
 */


public class GrideViewRemoteService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new GridViewRemoteFactory(this.getApplicationContext());
    }
}

 class GridViewRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    String [] images;
    String [] txt={"hey","Hello","mar7aba"};
    List<Bitmap> photos;

    public GridViewRemoteFactory(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {

        photos= LocalStorageUtilies.getDownloadedPhotos();

    }

    @Override
    public void onDataSetChanged() {
        photos= LocalStorageUtilies.getDownloadedPhotos();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public RemoteViews getViewAt( int i) {

        final RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_grid_item);

        remoteViews.setImageViewBitmap(R.id.img_photo,photos.get(i));


        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }



}
