package com.cenah.efficentlearning.notificationService;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.models.NotificationModel;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.User;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.NotificationService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceNotifications extends Service {
    boolean serviceIsOn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ServiceDemo", "Service Destroyed");
        serviceIsOn = false;
        getData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceDemo", "In OnStartCommand,Thread ID: " + Thread.currentThread().getId());
        //stopSelf();//  to stop the service
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceIsOn = true;
        getData();

    }

    private void getData() {
        final Handler handler = new Handler();
        Shared userRole = new Apm(ServiceNotifications.this).getSharedInfo();
        if(!userRole.getUserRole().getRoleName().equals("Student")){
            serviceIsOn = false;
        }
        if (serviceIsOn) {
            NotificationService taskService = new RestFullHelper().getNotificationClient();
            //Call<NotaficationMainModel> call = taskService.getNotaficatons(new ApplicationPreferenceManager(this).getSharedInfo().getMemberId().toUpperCase(), new ApplicationPreferenceManager(this).getLastNotification());
            Call<ArrayList<NotificationModel>> call = taskService.GetNotifications("Bearer " + userRole.getAuth().token);

            String temp = call.request().url().toString();
            call.enqueue(new Callback<ArrayList<NotificationModel>>() {

                @Override
                public void onResponse(@NonNull Call<ArrayList<NotificationModel>> call, @NonNull Response<ArrayList<NotificationModel>> response) {
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            if (!response.body().isEmpty()) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setAction("com.example.Broadcast");
                                    intent.putExtra("msg", response.body());
                                    sendBroadcast(intent);
                                } catch (Exception ignored) {
                                }

                            }
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                }
                            }, 15000);

                          /*  if (response.body().size() != 0){
                                Date maxDate = response.body().stream().map(NotificationModel::getDate).max(Date::compareTo).get();
                            }
                                new Apm(getApplicationContext())
                                        .saveQuestion(mainTaskModel.getData().get(mainTaskModel.getData().size() - 1).getSort());*/

                        } else
                            Toast.makeText(ServiceNotifications.this, "Not ERROR", Toast.LENGTH_LONG).show();

                    } else
                        Toast.makeText(ServiceNotifications.this, "Not ERROR", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<NotificationModel>> call, @NonNull Throwable t) {
                    //Toast.makeText(ServiceNotifications.this, t.getMessage().trim(), Toast.LENGTH_LONG).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                        }
                    }, 15000);


                }
            });
        }

    }
}
