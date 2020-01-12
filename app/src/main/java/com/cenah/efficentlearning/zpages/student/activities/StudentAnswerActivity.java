package com.cenah.efficentlearning.zpages.student.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Material;
import com.cenah.efficentlearning.models.MaterialAnswer;
import com.cenah.efficentlearning.models.MaterialAnswerPostModel;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.MaterialService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
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
    private TextView tx_score, tx_createdDate;
    private LinearLayout ln_score;
    private MaterialAnswer myAnswer;

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

        setToolBar();

        btn_confirm = findViewById(R.id.btn_confirm);
        tx_answer = findViewById(R.id.tx_answer);
        tx_score = findViewById(R.id.tx_score);
        tx_createdDate = findViewById(R.id.tx_createdDate);
        ln_score = findViewById(R.id.ln_score);

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
        waitBar.show();
        MaterialAnswerPostModel postModel = new MaterialAnswerPostModel(tx_answer.getText().toString().trim(),
                thisQuestion.getId());
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        materialService.PostAnswer("bearer " + new Apm(activity).getSharedInfo().getAuth().token, postModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                getAnswer();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAnswer() {
        waitBar.show();
        String auth = new Apm(activity).getSharedInfo().getAuth().token;
        materialService.GetMaterialAnswers("bearer " + new Apm(activity).getSharedInfo().getAuth().token, thisQuestion.getId())
                .enqueue(new Callback<ArrayList<MaterialAnswer>>() {
                    @Override
                    public void onResponse(@NotNull Call<ArrayList<MaterialAnswer>> call,
                                           @NotNull Response<ArrayList<MaterialAnswer>> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            Toast.makeText(activity, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int userId = Math.round(new Apm(activity).getSharedInfo().getUserRole().getId());

                        if (response.body() != null) {
                            for (MaterialAnswer answer : response.body()) {
                                if (answer.getUserId() == userId) {
                                    changeView();
                                    myAnswer = answer;
                                    tx_answer.setText(myAnswer.getAnswer() + "");
                                    tx_createdDate.setText(DateHelper.dateToString(myAnswer.getCreationTime()) + "");
                                    tx_score.setText(myAnswer.getScore() + "");
                                    return;
                                }
                            }
                        }


                    }

                    @Override
                    public void onFailure(@NotNull Call<ArrayList<MaterialAnswer>> call,
                                          @NotNull Throwable t) {
                        waitBar.hide();
                        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void changeView() {
        ln_score.setVisibility(View.VISIBLE);
        btn_confirm.setVisibility(View.GONE);
    }
}
