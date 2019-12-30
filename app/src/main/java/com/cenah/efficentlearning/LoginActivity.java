package com.cenah.efficentlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.AuthMainPageIntent;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.UserService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserService userService;
    private EditText edtUserName, edtPass;
    private WaitBar waitBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtUserName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPass);
        waitBar = new WaitBar(LoginActivity.this);


        userService = new RestFullHelper().getUnsafeUserClient();
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
//                finish();
                waitBar.show();
                AuthBody body = new AuthBody(edtUserName.getText().toString().trim(), edtPass.getText().toString().trim());
                Call<Auth> call = userService.Login(body);
                call.enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(@NotNull Call<Auth> call, @NotNull Response<Auth> response) {
                        waitBar.hide();
                        if (!response.isSuccessful()) {
                            if (response.code() == 400)
                                Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(LoginActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        GetUserWithRole(response.body());

                    }

                    @Override
                    public void onFailure(@NotNull Call<Auth> call, @NotNull Throwable t) {
                        waitBar.hide();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


    }

    private void GetUserWithRole(final Auth body) {
        waitBar.show();
        Call<UserRole> call = userService.GetUserWithRole("Bearer " +body.token);
        call.enqueue(new Callback<UserRole>() {
            @Override
            public void onResponse(Call<UserRole> call, Response<UserRole> response) {
                waitBar.hide();
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                     return;
                }
                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                new Apm(getApplicationContext()).saveSharedInfo(new Shared(response.body(),body));
                new AuthMainPageIntent(response.body(),LoginActivity.this).Page();


            }

            @Override
            public void onFailure(Call<UserRole> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
