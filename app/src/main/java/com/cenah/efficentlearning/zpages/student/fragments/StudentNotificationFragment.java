package com.cenah.efficentlearning.zpages.student.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.NotificaitonMeterial;
import com.cenah.efficentlearning.models.NotificationGlobalModel;
import com.cenah.efficentlearning.models.NotificationModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.NotificationService;
import com.cenah.efficentlearning.zpages.student.adapters.NotificationAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentNotificationFragment extends Fragment implements NotificationAdapter.OnClick {

    private View rootView;
    private WaitBar waitBar;
    private Activity activity;
    private RecyclerView recyclerView;
    private NotificationService service;

    private ArrayList<NotificationGlobalModel> list ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_student_notification, container, false);

        activity = getActivity();
        waitBar = new WaitBar(activity);

        service = new RestFullHelper().getNotificationClient();

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        getNotifications();


        return rootView;
    }

    private void getNotifications() {
        list = new ArrayList<>();
        waitBar.show();
        String auth = "Bearer " + new Apm(activity).getSharedInfo().getAuth().token;
        service.GetNotifications(auth).enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<NotificationModel>> call, @NotNull Response<ArrayList<NotificationModel>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()!=null)
                for (NotificationModel model:response.body())
                    list.add(new NotificationGlobalModel(model.getDate(),model.getMessage(),
                            null,null,null,null,null));

                getOtherNotifications();
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getOtherNotifications() {
        waitBar.show();
        String auth = "Bearer " + new Apm(activity).getSharedInfo().getAuth().token;
        service.GetFreshMaterials(auth).enqueue(new Callback<ArrayList<NotificaitonMeterial>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<NotificaitonMeterial>> call, @NotNull Response<ArrayList<NotificaitonMeterial>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()!=null)
                    for (NotificaitonMeterial model:response.body())
                        list.add(new NotificationGlobalModel(null,null,
                                model.getId(),model.getGivenClassroomId(),model.getDescription(),model.getDeadline(),model.getCreationTime()));


                recyclerView.setAdapter(new NotificationAdapter(activity, StudentNotificationFragment.this, list));

            }

            @Override
            public void onFailure(Call<ArrayList<NotificaitonMeterial>> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void click(NotificationGlobalModel model, int position, View view) {

    }
}
