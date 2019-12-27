package com.cenah.efficentlearning.zpages.teacher.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.zpages.teacher.activites.TeacherClassesActivity;
import com.cenah.efficentlearning.zpages.teacher.activites.TeachserStudentActivity;


public class TeacherMainPageFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student_main_page2, container, false);

        clicklistners();

        return rootView;
    }

    private void clicklistners() {

        rootView.findViewById(R.id.btnStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TeachserStudentActivity.class));
            }
        });
        rootView.findViewById(R.id.btnClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TeacherClassesActivity.class));
            }
        });

    }

}
