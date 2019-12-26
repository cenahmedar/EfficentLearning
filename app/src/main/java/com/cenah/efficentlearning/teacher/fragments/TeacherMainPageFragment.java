package com.cenah.efficentlearning.teacher.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.efficentlearning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherMainPageFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student_main_page2, container, false);

        clicklistners();

        return rootView;
    }

    private void clicklistners() {

     /*   rootView.findViewById(R.id.btnStudent).setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

}
