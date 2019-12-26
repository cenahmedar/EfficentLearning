package com.cenah.efficentlearning.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.WaitBar;

import java.util.Objects;

public class AdminCourseActivity extends AppCompatActivity {

    private WaitBar waitBar;
    private Toolbar toolbar;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);
        activity = this;
        waitBar = new WaitBar(activity);

        setToolBar();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Courses");
        ((AppCompatActivity) Objects.requireNonNull(activity)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // toolbar.setSubtitle(activity.getString(R.string.today));
    }
}
