package com.dev.mohamed.partyphotos.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dev.mohamed.partyphotos.R;

/**
 * Created by moham on 7/9/2018.
 */

public class FavouritPartyWidgetService extends IntentService {
    public FavouritPartyWidgetService() {
        super("FavouritPartyWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent!=null) {
            updateList();

        }
    }


    public static void upadateWidgetList(Context context)
    {
        Intent intent=new Intent(context,FavouritPartyWidgetService.class);
        context.startService(intent);
    }


    private void updateList()
    {
        AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(this);
        int []appIds=appWidgetManager.getAppWidgetIds(new ComponentName(this,FavouritePartyWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appIds, R.id.grid);

        FavouritePartyWidgetProvider.updateWidget(this,appWidgetManager,appIds);
    }
}
