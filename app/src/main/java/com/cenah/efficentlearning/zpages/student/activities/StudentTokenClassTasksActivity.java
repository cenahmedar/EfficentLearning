package com.cenah.efficentlearning.zpages.student.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.TokenClasses;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;
import com.cenah.efficentlearning.zpages.student.adapters.StudentTaskAdapter;
import com.cenah.efficentlearning.zpages.teacher.activites.TeacherClassTaskActivity;
import com.cenah.efficentlearning.zpages.teacher.adapters.TeacherTaskAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentTokenClassTasksActivity extends AppCompatActivity implements StudentTaskAdapter.OnStudentClick {

    private WaitBar waitBar;
    private Activity activity;
    private MaterialService materialService;
    private RecyclerView recyclerView;
    private TokenClasses thisClass;

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tasks");
        ((AppCompatActivity) Objects.requireNonNull(activity)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_token_class_tasks);
        activity = this;
        materialService = new RestFullHelper().getMaterialClient();
        waitBar = new WaitBar(activity);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        thisClass = new Apm(activity).getTokenTask();

        TextView tx_class_name = findViewById(R.id.tx_class_name);
        tx_class_name.setText(thisClass.getCourseName() + "");

        TextView tx_description = findViewById(R.id.tx_description);
        tx_description.setText(thisClass.getGivenClassroomDescription() + "");


        setToolBar();

        getAll();

    }

    private void getAll() {
        waitBar.show();
        materialService.GetMaterialsForClass("bearer " + new Apm(activity).getSharedInfo().getAuth().token, thisClass.getGivenClassroomId())
                .enqueue(new Callback<ArrayList<Material>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Material>> call, Response<ArrayList<Material>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        recyclerView.setAdapter(new StudentTaskAdapter(activity, StudentTokenClassTasksActivity.this, response.body()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Material>> call, Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(Material model, int position, View view) {

        new Apm(getApplicationContext()).saveQuestion(model);
        startActivity(new Intent(StudentTokenClassTasksActivity.this, StudentAnswerActivity.class));
    }
}
