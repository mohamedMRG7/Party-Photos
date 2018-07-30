package com.dev.mohamed.partyphotos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by moham on 6/14/2018.
 */

public class Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"hey",Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context,MainActivity.class));
        Log.e("camera","camera on");
    }
}
