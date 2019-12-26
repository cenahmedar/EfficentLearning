package com.cenah.efficentlearning.admin.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.cenah.efficentlearning.LoginActivity;
import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.ApplicationPreferenceManager;
import com.cenah.efficentlearning.helpers.PrograssBarDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminProfileFragment extends Fragment {


    private PrograssBarDialog prograssBarDialog;
    private Activity activity;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        activity = getActivity();

        prograssBarDialog = new PrograssBarDialog(getActivity());


        rootView.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ApplicationPreferenceManager(activity).deleteSharedInfo();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        });

        return rootView;
    }

}
