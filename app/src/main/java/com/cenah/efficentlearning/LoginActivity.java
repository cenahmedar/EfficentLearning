package com.cenah.efficentlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cenah.efficentlearning.admin.activities.AdminHomeActivity;
import com.cenah.efficentlearning.helpers.ApplicationPreferenceManager;
import com.cenah.efficentlearning.helpers.AuthMainPageIntent;
import com.cenah.efficentlearning.helpers.PrograssBarDialog;
import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.User;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.rest.RestFullHelper;
import com.cenah.efficentlearning.rest.services.UserService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserService userService;
    private EditText edtUserName, edtPass;
    private PrograssBarDialog prograssBarDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtUserName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPass);
        prograssBarDialog = new PrograssBarDialog(LoginActivity.this);


        userService = new RestFullHelper().getUnsafeUserClient();
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
//                finish();
                prograssBarDialog.show();
                AuthBody body = new AuthBody(edtUserName.getText().toString().trim(), edtPass.getText().toString().trim());
                Call<Auth> call = userService.Login(body);
                call.enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(@NotNull Call<Auth> call, @NotNull Response<Auth> response) {
                        if (!response.isSuccessful()) {
                            if (response.code() == 400)
                                Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(LoginActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        prograssBarDialog.hide();
                        GetUserWithRole(response.body());

                    }

                    @Override
                    public void onFailure(@NotNull Call<Auth> call, @NotNull Throwable t) {
                        prograssBarDialog.hide();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void GetUserWithRole(final Auth body) {
        prograssBarDialog.show();
        Call<UserRole> call = userService.GetUserWithRole("Bearer " +body.token);
        call.enqueue(new Callback<UserRole>() {
            @Override
            public void onResponse(Call<UserRole> call, Response<UserRole> response) {
                prograssBarDialog.hide();
                if (!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                     return;
                }
                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                new ApplicationPreferenceManager(getApplicationContext()).saveSharedInfo(new Shared(response.body(),body));
                new AuthMainPageIntent(response.body(),LoginActivity.this).Page();


            }

            @Override
            public void onFailure(Call<UserRole> call, Throwable t) {
                prograssBarDialog.hide();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
