package com.cenah.efficentlearning.notificationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cenah.efficentlearning.models.NotificationModel;

import java.util.ArrayList;
import java.util.Objects;

public class NotafictionReciver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (intent.getAction().equalsIgnoreCase("com.example.Broadcast")){
            assert bundle != null;
            //Toast.makeText(context,bundle.getString("msg"),Toast.LENGTH_LONG).show();

          /*  NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
            nb.setContentText(bundle.getString("msg"));
            nb.setContentTitle("HELLo");
            nb.setSmallIcon(R.drawable.argesmall);

            NotificationManager nm = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1,nb.build());*/

            ArrayList<NotificationModel> temp = (ArrayList<NotificationModel>) intent.getSerializableExtra("msg");
            //Toast.makeText(context, temp.size() + "", Toast.LENGTH_LONG).show();


            if(temp.size()!=0)
                for (int i = 0; i <temp.size() ; i++) {
                    MainNotification mainNotification = new MainNotification();
                    mainNotification.notify(context,temp.get(i),i);
                }



        }

    }
}
