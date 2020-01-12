package com.cenah.efficentlearning.zpages.admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.zpages.admin.adapters.AdminTeacherAdapter;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.PasswordValidation;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Teacher;
import com.cenah.efficentlearning.models.TeacherCreateModel;
import com.cenah.efficentlearning.models.TeacherUpdateModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.TeacherService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTeacherActivity extends AppCompatActivity implements AdminTeacherAdapter.OnClick {

    private WaitBar waitBar;
    private Toolbar toolbar;
    private Activity activity;
    private RecyclerView recyclerView;
    private TeacherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher);
        activity = this;
        waitBar = new WaitBar(activity);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        service = new RestFullHelper().getTeacherClient();

        setToolBar();
        getTeachers();

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTeacherDialog();
            }
        });


    }

    private void addTeacherDialog() {
        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_teacher_dialog, null);

        final TextView ed_name = subView.findViewById(R.id.ed_name);
        final TextView ed_surname = subView.findViewById(R.id.ed_surname);
        final TextView ed_email = subView.findViewById(R.id.ed_email);
        final TextView ed_username = subView.findViewById(R.id.ed_username);
        final TextView ed_password = subView.findViewById(R.id.ed_password);
        final TextView ed_cpassword = subView.findViewById(R.id.ed_cpassword);

        TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        TextView btn_ok = subView.findViewById(R.id.btn_ok);


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        builder.setView(subView);
        final android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TeacherCreateModel teacher = new TeacherCreateModel(ed_name.getText().toString().trim(), ed_surname.getText().toString().trim(), ed_username.getText().toString().trim(),
                        ed_email.getText().toString().trim(), ed_password.getText().toString().trim(), ed_cpassword.getText().toString().trim());

                if (teacher.getConfirmPassword().isEmpty() || teacher.getEmail().isEmpty() ||
                        teacher.getName().isEmpty() || teacher.getPassword().isEmpty() ||
                        teacher.getSurname().isEmpty() || teacher.getUsername().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!teacher.getPassword().equals(teacher.getConfirmPassword())) {
                    Toast.makeText(activity, "Password does not match!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (teacher.getPassword().length() < 7) {
                    Toast.makeText(activity, "Password is too short!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!new PasswordValidation().pattern(teacher.getPassword())) {
                    Toast.makeText(activity, "Password must have at least one special character!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                addTeacher(teacher, alertDialog);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }

    private void addTeacher(TeacherCreateModel teacher, final AlertDialog alertDialog) {
        waitBar.show();

        Call<TeacherCreateModel> call = service.Post("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, teacher);
        call.enqueue(new Callback<TeacherCreateModel>() {
            @Override
            public void onResponse(Call<TeacherCreateModel> call, Response<TeacherCreateModel> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminTeacherActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AdminTeacherActivity.this, "success", Toast.LENGTH_SHORT).show();
                getTeachers();

                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TeacherCreateModel> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTeachers() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<Teacher>> call = service.GetAll("Bearer " + new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<Teacher>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Teacher>> call, @NotNull Response<ArrayList<Teacher>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminTeacherActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new AdminTeacherAdapter(activity, AdminTeacherActivity.this, response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Teacher>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    @Override
    public void click(final Teacher model, int position, View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.admin_teacher_pop, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit) {
                    editDialog(model);
                    return true;
                }
                if (item.getItemId() == R.id.menu_delete) {
                    deleteTeacher(model);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void editDialog(final Teacher model) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.edit_teacher_dialog, null);

        final TextView ed_name = subView.findViewById(R.id.ed_name);
        final TextView ed_surname = subView.findViewById(R.id.ed_surname);

        TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        TextView btn_ok = subView.findViewById(R.id.btn_ok);


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        ed_name.setText(model.getName());
        ed_surname.setText(model.getSurname());

        builder.setView(subView);
        final android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_name.getText().toString().trim().isEmpty() || ed_surname.getText().toString().trim().isEmpty()){
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                model.setName(ed_name.getText().toString().trim());
                model.setSurname(ed_surname.getText().toString().trim());
                editTeacher(model, alertDialog);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void deleteTeacher(Teacher model) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Teacher> call = service.Delete("Bearer " + new Apm(activity).getSharedInfo().getAuth().token,model.getId());

        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminTeacherActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AdminTeacherActivity.this, "success", Toast.LENGTH_SHORT).show();
                getTeachers();

            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editTeacher(Teacher model,final AlertDialog alertDialog) {
        TeacherUpdateModel teacherUpdateModel = new TeacherUpdateModel(model.getName(),model.getSurname());
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Teacher> call = service.Update("Bearer " + new Apm(activity).getSharedInfo().getAuth().token,teacherUpdateModel);

        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminTeacherActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Toast.makeText(AdminTeacherActivity.this, "success", Toast.LENGTH_SHORT).show();
                getTeachers();

            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminTeacherActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
