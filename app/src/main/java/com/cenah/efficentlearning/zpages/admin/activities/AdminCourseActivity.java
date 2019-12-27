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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.zpages.admin.adapters.AdminCourseAdapter;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Course;
import com.cenah.efficentlearning.models.CourseCreateModel;
import com.cenah.efficentlearning.models.ProgramingType;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.CourseService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCourseActivity extends AppCompatActivity implements AdminCourseAdapter.OnStudentClick {

    private WaitBar waitBar;
    private Toolbar toolbar;
    private Activity activity;
    private RecyclerView recyclerView;
    private CourseService service;
    private int programingType =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);
        activity = this;
        waitBar = new WaitBar(activity);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        service = new RestFullHelper().getCourseClient();

        setToolBar();
        getAll();

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTypes(null);
            }
        });
    }

    private void getTypes(final Course editCourse) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<ProgramingType>> call = service.GetAllProgrammingTypes("Bearer " + new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<ProgramingType>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<ProgramingType>> call, @NotNull Response<ArrayList<ProgramingType>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminCourseActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                if (editCourse != null)
                    editDialog(editCourse, response.body());
                else
                    addDialog(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<ProgramingType>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminCourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDialog(ArrayList<ProgramingType> types) {
        programingType = -1;
        ArrayAdapter<ProgramingType> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, types);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_course_dialog, null);

        final TextView ed_name = subView.findViewById(R.id.ed_name);
        final Spinner spinner = subView.findViewById(R.id.spinner);

        final TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        final TextView btn_ok = subView.findViewById(R.id.btn_ok);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        spinner.setAdapter(adapter);
        builder.setView(subView);
        final android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name.getText().toString().trim().isEmpty() || programingType == -1) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                CourseCreateModel model = new CourseCreateModel(ed_name.getText().toString().trim(), programingType);
                addCourse(model, alertDialog);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorCharcoalGray));
                ProgramingType model = (ProgramingType) spinner.getItemAtPosition(position);
                programingType = model.getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addCourse(CourseCreateModel model, final AlertDialog alertDialog) {
        waitBar.show();

        Call<Course> call = service.Post("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model);
        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminCourseActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AdminCourseActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminCourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAll() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<Course>> call = service.GetAll("Bearer " + new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<Course>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Course>> call, @NotNull Response<ArrayList<Course>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminCourseActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new AdminCourseAdapter(activity, AdminCourseActivity.this, response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Course>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminCourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public void onClick(final Course model, int position, View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.teacher_pop, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit) {
                    getTypes(model);
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

    private void editDialog(final Course model, ArrayList<ProgramingType> types) {
        ArrayAdapter<ProgramingType> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, types);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_course_dialog, null);

        final TextView ed_name = subView.findViewById(R.id.ed_name);
        final Spinner spinner = subView.findViewById(R.id.spinner);

        final TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        final TextView btn_ok = subView.findViewById(R.id.btn_ok);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        ed_name.setText(model.getName());
        spinner.setAdapter(adapter);


        builder.setView(subView);
        final android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_name.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                model.setName(ed_name.getText().toString().trim());
                editCourse(model, alertDialog);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorCharcoalGray));
                ProgramingType  type = (ProgramingType) spinner.getItemAtPosition(position);
                model.setProgrammingType(type.getKey());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int pos = 0;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getKey() == model.getProgrammingType()) {
                pos = i;
                break;
            }
        }
        spinner.setSelection(pos);
    }

    private void editCourse(Course model, final AlertDialog alertDialog) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Course> call = service.Update("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model);

        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminCourseActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Toast.makeText(AdminCourseActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminCourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteTeacher(Course model) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Course> call = service.Delete("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model.getId());

        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(AdminCourseActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AdminCourseActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(AdminCourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
