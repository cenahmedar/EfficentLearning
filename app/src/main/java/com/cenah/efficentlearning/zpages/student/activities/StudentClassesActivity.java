package com.cenah.efficentlearning.zpages.student.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.JoinClassModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.ClassService;
import com.cenah.efficentlearning.zpages.student.adapters.StudentClasssAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentClassesActivity extends AppCompatActivity implements StudentClasssAdapter.OnStudentClick{

    private WaitBar waitBar;
    private Activity activity;
    private ClassService classService;
    private RecyclerView recyclerView;

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Classes");
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
        setContentView(R.layout.activity_student_classes);
        activity = this;
        waitBar = new WaitBar(activity);
        classService = new RestFullHelper().getClassClient();


        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setToolBar();

        getAll();
    }

    private void getAll() {
        waitBar.show();
        classService.GetAll("Bearer " + new Apm(activity).getSharedInfo().getAuth().token)
                .enqueue(new Callback<ArrayList<Classes>>() {
                    @Override
                    public void onResponse(@NotNull Call<ArrayList<Classes>> call, @NotNull Response<ArrayList<Classes>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(new StudentClasssAdapter(activity, StudentClassesActivity.this, response.body()));

                    }

                    @Override
                    public void onFailure(@NotNull Call<ArrayList<Classes>> call, @NotNull Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(Classes model, int position, View view) {
        JoinClassModel joinClassModel = new JoinClassModel(model.getId());
        classService.JoinClass("Bearer " + new Apm(activity).getSharedInfo().getAuth().token,joinClassModel)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            if(response.code()==400){
                                Toast.makeText(activity, "You already joined this class!!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
