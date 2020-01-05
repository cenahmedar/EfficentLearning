package com.cenah.efficentlearning.zpages.student.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.MaterialAnswer;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAnswerActivity extends AppCompatActivity {

    private WaitBar waitBar;
    private Activity activity;
    private MaterialService materialService;
    private Material thisQuestion;
    private Button btn_confirm;
    private EditText tx_answer;
    private TextView tx_score;

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Answer");
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
        setContentView(R.layout.activity_student_answer);
        activity = this;
        materialService = new RestFullHelper().getMaterialClient();
        waitBar = new WaitBar(activity);

        btn_confirm = findViewById(R.id.btn_confirm);
        tx_answer = findViewById(R.id.tx_answer);
        tx_score = findViewById(R.id.tx_score);

        thisQuestion = new Apm(activity).getQuestion();

        TextView tx_class_name = findViewById(R.id.tx_class_name);
        tx_class_name.setText(thisQuestion.getQuestion() + "");

        TextView tx_description = findViewById(R.id.tx_description);
        tx_description.setText(thisQuestion.getHint() + "");


        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAnswer();
            }
        });
        getAnswer();
    }

    private void confirmAnswer() {


    }

    private void getAnswer() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        materialService.GetMaterialAnswers("bearer " + new Apm(activity).getSharedInfo().getAuth().token,thisQuestion.getId())
                .enqueue(new Callback<ArrayList<MaterialAnswer>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MaterialAnswer>> call, Response<ArrayList<MaterialAnswer>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!response.body().isEmpty()){

                           // int userId = new Apm(activity).get

                            for (MaterialAnswer answer: response.body()) {

                            }
                        }
                        else
                            changeView();



                    }

                    @Override
                    public void onFailure(Call<ArrayList<MaterialAnswer>> call, Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

    private void changeView() {

    }
}
