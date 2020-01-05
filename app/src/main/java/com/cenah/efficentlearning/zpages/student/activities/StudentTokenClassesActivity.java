package com.cenah.efficentlearning.zpages.student.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.ClassCreateModel;
import com.cenah.efficentlearning.models.TokenClasses;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.ClassService;
import com.cenah.efficentlearning.zpages.student.adapters.TokenClassAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentTokenClassesActivity extends AppCompatActivity implements TokenClassAdapter.OnStudentClick {


    private WaitBar waitBar;
    private Activity activity;
    private ClassService classService;
    private RecyclerView recyclerView;

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Token Classes");
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
        setContentView(R.layout.activity_student_token_classes);
        activity = this;
        waitBar = new WaitBar(activity);
        classService = new RestFullHelper().getClassClient();


        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setToolBar();

        getAll();

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    private void getAll() {
        waitBar.show();
        classService.GetTakenClassroom("Bearer " + new Apm(activity).getSharedInfo().getAuth().token)
                .enqueue(new Callback<ArrayList<TokenClasses>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<TokenClasses>> call, @NotNull Response<ArrayList<TokenClasses>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new TokenClassAdapter(activity, StudentTokenClassesActivity.this, response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<TokenClasses>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(final TokenClasses model, int position, View view) {
        final PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.token_class_pop, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_task) {
                    new Apm(getApplicationContext()).saveTokenTask(model);
                    startActivity(new Intent(StudentTokenClassesActivity.this,StudentTokenClassTasksActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    delete(model);
                    return true;
                }
                return  false;
            }
        });
        popup.show();
    }

    private void delete(TokenClasses model) {

        waitBar.show();
        classService.DeleteJoin("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model.getTakenClassroomUserId()).
                enqueue(new Callback<ClassCreateModel>() {
            @Override
            public void onResponse(Call<ClassCreateModel> call, Response<ClassCreateModel> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<ClassCreateModel> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
