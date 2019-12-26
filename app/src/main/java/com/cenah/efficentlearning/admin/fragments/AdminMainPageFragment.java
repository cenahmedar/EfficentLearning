package com.cenah.efficentlearning.admin.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.admin.activities.AdminCourseActivity;
import com.cenah.efficentlearning.admin.activities.AdminStudentActivity;
import com.cenah.efficentlearning.admin.activities.AdminTeacherActivity;
import com.cenah.efficentlearning.admin.activities.AdminUserActivity;
import com.cenah.efficentlearning.helpers.WaitBar;


public class AdminMainPageFragment extends Fragment {

    private View rootView;
    private WaitBar waitBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_main_page, container, false);

        waitBar = new WaitBar(getActivity());
        clicklistners();

        return rootView;
    }

    private void clicklistners() {

        rootView.findViewById(R.id.btnStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminStudentActivity.class));
            }
        });
        rootView.findViewById(R.id.btnTeacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminTeacherActivity.class));
            }
        });
        rootView.findViewById(R.id.btnCourse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminCourseActivity.class));
            }
        });
        rootView.findViewById(R.id.btnAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminUserActivity.class));
            }
        });
    }


}
