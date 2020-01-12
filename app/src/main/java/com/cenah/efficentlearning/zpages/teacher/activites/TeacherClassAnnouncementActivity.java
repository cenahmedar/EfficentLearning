package com.cenah.efficentlearning.zpages.teacher.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Announcement;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;
import com.cenah.efficentlearning.zpages.teacher.adapters.TeacherAnnocAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassAnnouncementActivity extends AppCompatActivity implements TeacherAnnocAdapter.OnStudentClick {

    private WaitBar waitBar;
    private Activity activity;
    private RecyclerView recyclerView;
    private MaterialService materialService;
    private Classes thisClass;


    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Announcement");
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
        setContentView(R.layout.activity_teacher_class_announcement);

        activity = this;
        waitBar = new WaitBar(activity);

        materialService = new RestFullHelper().getMaterialClient();

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        thisClass = new Apm(activity).getClasses();

        TextView tx_class_name = findViewById(R.id.tx_class_name);
        tx_class_name.setText(thisClass.getDescription() + "");
        setToolBar();

        getAll();

    /*    findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/


    }

    private void getAll() {
        waitBar.show();
        materialService.getAnnon("bearer " + new Apm(activity).getSharedInfo().getAuth().token, thisClass.getId())
                .enqueue(new Callback<ArrayList<Announcement>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Announcement>> call, Response<ArrayList<Announcement>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        recyclerView.setAdapter(new TeacherAnnocAdapter(activity, TeacherClassAnnouncementActivity.this, response.body()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Announcement>> call, Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onClick(Announcement model, int position, View view) {

    }
}
