package com.cenah.efficentlearning.zpages.teacher.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.zpages.admin.adapters.AdimStudentAdapter;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Student;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.StudentService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachserStudentActivity extends AppCompatActivity implements  AdimStudentAdapter.OnStudentClick  {

    private WaitBar waitBar;
    private Toolbar toolbar;
    private Activity activity;
    private StudentService studentService;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachser_student);
        waitBar = new WaitBar(TeachserStudentActivity.this);
        activity = this;
        setToolBar();

        recyclerView = findViewById(R.id.recyclerView);

        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(activity).getOrientation());
        //  recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        studentService = new RestFullHelper().getStudentClient();
        getStudents();

    }

    private void getStudents() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<Student>> call = studentService.GetAllStudents("Bearer " +new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<Student>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Student>> call, @NotNull Response<ArrayList<Student>> response) {
                waitBar.hide();
                if (!response.isSuccessful()){
                    Toast.makeText(TeachserStudentActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new AdimStudentAdapter(activity, TeachserStudentActivity.this, response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Student>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(TeachserStudentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Students");
        ((AppCompatActivity) Objects.requireNonNull(activity)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // toolbar.setSubtitle(activity.getString(R.string.today));
    }

    @Override
    public void onClick(Student model, int position, View view) {

    }
}

