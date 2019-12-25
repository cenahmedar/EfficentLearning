package com.cenah.efficentlearning.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.PrograssBarDialog;

import java.util.Objects;

public class AdminTeacherActivity extends AppCompatActivity {

    private PrograssBarDialog prograssBarDialog;
    private Toolbar toolbar;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher);
        activity = this;
        prograssBarDialog = new PrograssBarDialog(activity);

        setToolBar();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Teachers");
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
