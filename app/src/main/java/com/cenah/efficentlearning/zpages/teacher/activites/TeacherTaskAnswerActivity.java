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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.MaterialAnswer;
import com.cenah.efficentlearning.models.PointModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;
import com.cenah.efficentlearning.zpages.teacher.adapters.TeacherAnswerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherTaskAnswerActivity extends AppCompatActivity implements TeacherAnswerAdapter.OnStudentClick{

    private WaitBar waitBar;
    private Activity activity;
    private RecyclerView recyclerView;
    private MaterialService materialService;
    private Material thisTask;


    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Answers");
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
        setContentView(R.layout.activity_teacher_task_answer);
        activity = this;
        waitBar = new WaitBar(activity);

        materialService = new RestFullHelper().getMaterialClient();

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        thisTask = new Apm(activity).getTask();

        TextView tx_class_name = findViewById(R.id.tx_class_name);
        tx_class_name.setText(thisTask.getQuestion() + "");
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
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        materialService.GetMaterialAnswers("bearer " + new Apm(activity).getSharedInfo().getAuth().token,thisTask.getId())
                .enqueue(new Callback<ArrayList<MaterialAnswer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MaterialAnswer>> call, Response<ArrayList<MaterialAnswer>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        recyclerView.setAdapter(new TeacherAnswerAdapter(activity, TeacherTaskAnswerActivity.this, response.body()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MaterialAnswer>> call, Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(MaterialAnswer model, int position, View view) {
      givePointDialog(model);
    }

    private void givePointDialog(final MaterialAnswer model) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View subView = inflater.inflate(R.layout.point_dialog, null);

        final EditText ed_score = subView.findViewById(R.id.ed_score);

        final TextView btn_cancel = subView.findViewById(R.id.btn_cancel);
        final TextView btn_ok = subView.findViewById(R.id.btn_ok);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);


        ed_score.setText(model.getScore()+"");

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
                if (ed_score.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PointModel pointModel = new PointModel(model.getUserId(),model.getMaterialId(),Integer.parseInt( ed_score.getText().toString().trim()));

                givePoint(pointModel, alertDialog);
            }
        });


    }

    private void givePoint(PointModel pointModel, final AlertDialog alertDialog) {


        waitBar.show();
        materialService.GivePoint("Bearer " + new Apm(activity).getSharedInfo().getAuth().token, pointModel).enqueue(new Callback<PointModel>() {
            @Override
            public void onResponse(Call<PointModel> call, Response<PointModel> response) {
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
            public void onFailure(Call<PointModel> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
