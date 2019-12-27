package com.cenah.efficentlearning.teacher.activites;

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
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.ClassCreateModel;
import com.cenah.efficentlearning.models.ClassUpdateModel;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.Course;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.ClassService;
import com.cenah.efficentlearning.restfull.services.CourseService;
import com.cenah.efficentlearning.teacher.adapters.TeacherClassAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassesActivity extends AppCompatActivity implements TeacherClassAdapter.OnStudentClick{

    private WaitBar waitBar;
    private Activity activity;
    private ClassService classService;
    private RecyclerView recyclerView;
    private CourseService courseService;
    private int selectedCouse =-1;

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
        setContentView(R.layout.activity_teacher_classes);
        activity = this;
        waitBar = new WaitBar(activity);
        classService = new RestFullHelper().getClassClient();
        courseService = new RestFullHelper().getCourseClient();


        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setToolBar();

        getAll();

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCourses(null);
            }
        });
    }

    private void getCourses(final Classes editClass) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<Course>> call = courseService.GetAll("Bearer " + new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<Course>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Course>> call, @NotNull Response<ArrayList<Course>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(TeacherClassesActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                if (editClass != null)
                    editDialog(editClass, response.body());
                else
                    addDialog(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Course>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(TeacherClassesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addDialog(ArrayList<Course> body) {
        selectedCouse = -1;
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, body);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_class_dialog, null);

        final TextView ed_desc = subView.findViewById(R.id.ed_desc);
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
                if (ed_desc.getText().toString().trim().isEmpty() || selectedCouse == -1) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ClassCreateModel model = new ClassCreateModel(selectedCouse,ed_desc.getText().toString().trim());
                addCourse(model, alertDialog);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorCharcoalGray));
                Course model = (Course) spinner.getItemAtPosition(position);
                selectedCouse = model.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void addCourse(ClassCreateModel model, final AlertDialog alertDialog) {
        waitBar.show();

        Call<ClassCreateModel> call = classService.Post("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model);
        call.enqueue(new Callback<ClassCreateModel>() {
            @Override
            public void onResponse(Call<ClassCreateModel> call, Response<ClassCreateModel> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(TeacherClassesActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TeacherClassesActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ClassCreateModel> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(TeacherClassesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAll() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<ArrayList<Classes>> call = classService.GetClassrooms("Bearer " +new Apm(activity).getSharedInfo().getAuth().token);
        call.enqueue(new Callback<ArrayList<Classes>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Classes>> call, @NotNull Response<ArrayList<Classes>> response) {
                waitBar.hide();
                if (!response.isSuccessful()){
                    Toast.makeText(TeacherClassesActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //  Toast.makeText(AdminStudentActivity.this, "success", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new TeacherClassAdapter(activity, TeacherClassesActivity.this, response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Classes>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(TeacherClassesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(final Classes model, int position, View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.class_pop, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit) {
                    getCourses(model);
                    return true;
                }
                if (item.getItemId() == R.id.menu_delete) {
                    delete(model);
                    return true;
                }
                if (item.getItemId() == R.id. menu_open) {
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void delete(Classes model) {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Classes> call = classService.Delete("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model.getId());

        call.enqueue(new Callback<Classes>() {
            @Override
            public void onResponse(Call<Classes> call, Response<Classes> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(TeacherClassesActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TeacherClassesActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<Classes> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(TeacherClassesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editDialog(final Classes editClass, ArrayList<Course> body) {
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, body);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_class_dialog, null);

        final TextView ed_desc = subView.findViewById(R.id.ed_desc);
        final Spinner spinner = subView.findViewById(R.id.spinner);

        final TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        final TextView btn_ok = subView.findViewById(R.id.btn_ok);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        ed_desc.setText(editClass.getDescription());
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
                if (ed_desc.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editClass.setDescription(ed_desc.getText().toString().trim());
                editCLass(editClass, alertDialog);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorCharcoalGray));
                Course  type = (Course) spinner.getItemAtPosition(position);
                editClass.setCourseId(type.getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int pos = 0;
        for (int i = 0; i < body.size(); i++) {
            if (body.get(i).getId() == editClass.getId()) {
                pos = i;
                break;
            }
        }
        spinner.setSelection(pos);
    }

    private void editCLass(Classes editClass, final AlertDialog alertDialog) {
        ClassUpdateModel updateModel = new ClassUpdateModel(editClass.getId(),editClass.getCourseId(),editClass.getDescription());
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        Call<Classes> call = classService.Update("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, updateModel);

        call.enqueue(new Callback<Classes>() {
            @Override
            public void onResponse(Call<Classes> call, Response<Classes> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(TeacherClassesActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Toast.makeText(TeacherClassesActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<Classes> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(TeacherClassesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
