package com.cenah.efficentlearning.zpages.teacher.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Classes;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.MaterialPostModel;
import com.cenah.efficentlearning.models.MaterialType;
import com.cenah.efficentlearning.models.MaterialUpdateModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;
import com.cenah.efficentlearning.zpages.teacher.adapters.TeacherTaskAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherClassTaskActivity extends AppCompatActivity implements TeacherTaskAdapter.OnStudentClick {

    private WaitBar waitBar;
    private Activity activity;
    private RecyclerView recyclerView;
    private MaterialService materialService;
    private int type;
    private Calendar date;
    private Classes thisClass;



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
        setContentView(R.layout.activity_teacher_class_detail);
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

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTypes();
            }
        });

    }

    private void getTypes() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;

        materialService.GetAllMaterialTypes("bearer " + new Apm(activity).getSharedInfo().getAuth().token).enqueue(new Callback<ArrayList<MaterialType>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<MaterialType>> call, @NotNull Response<ArrayList<MaterialType>> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                addDialog(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<MaterialType>> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDialog(ArrayList<MaterialType> body) {
        date = null;
        type = -1;
        ArrayAdapter<MaterialType> adapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item, body);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.add_task_dialog, null);

        final TextView ed_question = subView.findViewById(R.id.ed_question);
        final TextView ed_hint = subView.findViewById(R.id.ed_hint);
        final TextView ed_description = subView.findViewById(R.id.ed_description);
        final TextView ed_scale = subView.findViewById(R.id.ed_scale);

        final Button btn_date = subView.findViewById(R.id.btn_date);

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
                if (ed_question.getText().toString().trim().isEmpty() || ed_hint.getText().toString().trim().isEmpty() ||
                        ed_description.getText().toString().trim().isEmpty() ||  date==null|| ed_scale.getText().toString().trim().isEmpty() || type == -1) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                MaterialPostModel model = new MaterialPostModel(thisClass.getId(),type,Integer.parseInt(ed_scale.getText().toString().trim()) ,
                        ed_question.getText().toString().trim(),ed_hint.getText().toString().trim(),ed_description.getText().toString().trim(),DateHelper.calendarToJsonString(date));

                addTask(model, alertDialog);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorCharcoalGray));
                MaterialType model = (MaterialType) spinner.getItemAtPosition(position);
                type = model.getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar currentDate = Calendar.getInstance();
                date = Calendar.getInstance();
                new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                btn_date.setText(DateHelper.calendarToString(date));


                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

            }
        });
    }

    private void addTask(MaterialPostModel model, final AlertDialog alertDialog) {
        waitBar.show();
        materialService.Post("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                getAll();

                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAll() {
        waitBar.show();
        materialService.GetMaterialsForClass("bearer " + new Apm(activity).getSharedInfo().getAuth().token, new Apm(activity).getClasses().getId())
                .enqueue(new Callback<ArrayList<Material>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Material>> call, Response<ArrayList<Material>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        recyclerView.setAdapter(new TeacherTaskAdapter(activity, TeacherClassTaskActivity.this, response.body()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Material>> call, Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onClick(final Material model, int position, View view) {
        PopupMenu popup = new PopupMenu(activity, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.task_pop, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_edit) {
                    editDialog(model);
                    return true;
                }
                if (item.getItemId() == R.id.menu_delete) {
                    deleteTask(model);
                    return true;
                }

                if (item.getItemId() == R.id. menu_answer) {
                    new Apm(getApplicationContext()).saveTask(model);
                    startActivityForResult(new Intent(activity, TeacherTaskAnswerActivity.class), 1);

                    return true;
                }

                return false;
            }
        });
        popup.show();
    }

    private void editDialog(final Material model) {
        date = null;

        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.edit_task_dialog, null);

        final TextView ed_question = subView.findViewById(R.id.ed_question);
        final TextView ed_hint = subView.findViewById(R.id.ed_hint);
        final TextView ed_description = subView.findViewById(R.id.ed_description);
        final TextView ed_scale = subView.findViewById(R.id.ed_scale);



        final TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        final TextView btn_ok = subView.findViewById(R.id.btn_ok);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        ed_question.setText(model.getQuestion()+"");
        ed_hint.setText(model.getHint()+"");
        ed_description.setText("");
        ed_scale.setText(model.getMaterialScale()+"");


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
                if (ed_question.getText().toString().trim().isEmpty() || ed_hint.getText().toString().trim().isEmpty() ||
                        ed_description.getText().toString().trim().isEmpty() || ed_scale.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                MaterialUpdateModel update = new MaterialUpdateModel(model.getId(),
                        Integer.parseInt(ed_scale.getText().toString().trim()) ,
                        ed_question.getText().toString().trim()
                        ,ed_hint.getText().toString().trim()
                        ,ed_description.getText().toString().trim());

                updateTask(update, alertDialog);
            }
        });

    }

    private void updateTask(MaterialUpdateModel update, final AlertDialog alertDialog) {

        waitBar.show();
        materialService.Update("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, update).enqueue(new Callback<Material>() {
            @Override
            public void onResponse(Call<Material> call, Response<Material> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(Call<Material> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTask(Material model) {
        waitBar.show();
        String s = new Apm(activity).getSharedInfo().getAuth().token;
        materialService.Delete("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, model.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                getAll();

            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
