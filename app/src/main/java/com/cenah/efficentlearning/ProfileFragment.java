package com.cenah.efficentlearning;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private WaitBar waitBar;
    private Activity activity;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        activity = getActivity();

        waitBar = new WaitBar(getActivity());


        rootView.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Apm(activity).deleteSharedInfo();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        });

        return rootView;
    }

}
