package com.cenah.efficentlearning;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.SuccessModel;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.NotificationService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private WaitBar waitBar;
    private Activity activity;
    private View rootView;
    private Shared shared;
    private TextView fragment_profile_name_surname,
            tx_name, tx_surname, tx_email, tx_username, tx_point;
    private RatingBar ratingBar;
    private NotificationService service;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        activity = getActivity();

        waitBar = new WaitBar(getActivity());
        service = new RestFullHelper().getNotificationClient();
        shared = new Apm(activity).getSharedInfo();

        rootView.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Apm(activity).deleteSharedInfo();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        });

        fragment_profile_name_surname = rootView.findViewById(R.id.fragment_profile_name_surname);
        tx_name = rootView.findViewById(R.id.tx_name);
        tx_surname = rootView.findViewById(R.id.tx_surname);
        tx_email = rootView.findViewById(R.id.tx_email);
        tx_username = rootView.findViewById(R.id.tx_username);

        tx_point = rootView.findViewById(R.id.tx_point);
        ratingBar = rootView.findViewById(R.id.ratingBar);

        fragment_profile_name_surname.setText(shared.getUserRole().getName() + " " + shared.getUserRole().getSurname());
        tx_name.setText(shared.getUserRole().getName());
        tx_surname.setText(shared.getUserRole().getSurname());
        tx_username.setText(shared.getUserRole().getUserName());
        tx_email.setText(shared.getUserRole().getEmail());

        if (shared.getUserRole().getRoleName().equals("Student"))
            getPoints();
        else{
            tx_point.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void getPoints() {
        waitBar.show();
        String auth = "Bearer " + new Apm(activity).getSharedInfo().getAuth().token;
        service.GetUserSuccess(auth).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(@NotNull Call<SuccessModel> call, @NotNull Response<SuccessModel> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                tx_point.setText(Math.round(response.body().getTotal())+"");
                ratingBar.setRating(response.body().getStar());

            }

            @Override
            public void onFailure(@NotNull Call<SuccessModel> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
