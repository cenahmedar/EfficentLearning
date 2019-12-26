package com.cenah.efficentlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cenah.efficentlearning.helpers.Apm;
import com.cenah.efficentlearning.helpers.AuthMainPageIntent;
import com.cenah.efficentlearning.helpers.PasswordValidation;
import com.cenah.efficentlearning.helpers.WaitBar;
import com.cenah.efficentlearning.models.Auth;
import com.cenah.efficentlearning.models.AuthBody;
import com.cenah.efficentlearning.models.Shared;
import com.cenah.efficentlearning.models.User;
import com.cenah.efficentlearning.models.UserRole;
import com.cenah.efficentlearning.restfull.RestFullHelper;
import com.cenah.efficentlearning.restfull.services.UserService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private UserService userService;
    private EditText tx_name, tx_surname,tx_username,tx_email,tx_password,tx_repassword;

    private WaitBar waitBar;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tx_name = findViewById(R.id.tx_name);
        tx_surname = findViewById(R.id.tx_surname);
        tx_username = findViewById(R.id.tx_username);
        tx_email = findViewById(R.id.tx_email);
        tx_password = findViewById(R.id.tx_password);
        tx_repassword = findViewById(R.id.tx_repassword);


        waitBar = new WaitBar(RegisterActivity.this);

        userService = new RestFullHelper().getUnsafeUserClient();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(tx_name.getText().toString().trim(), tx_surname.getText().toString().trim(), tx_username.getText().toString().trim(),
                        tx_email.getText().toString().trim(), tx_password.getText().toString().trim(), tx_repassword.getText().toString().trim());

                if (user.getConfirmpassword().isEmpty() || user.getEmail().isEmpty() ||
                        user.getName().isEmpty() || user.getPassword().isEmpty() ||
                        user.getSurname().isEmpty() || user.getUsername().isEmpty()) {
                    Toast.makeText(activity, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!user.getPassword().equals(user.getConfirmpassword())) {
                    Toast.makeText(activity, "Password does not match!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getPassword().length() < 7) {
                    Toast.makeText(activity, "Password is too short!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!new PasswordValidation().pattern(user.getPassword())) {
                    Toast.makeText(activity, "Password must have at least one special character!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                addUser(user);
            }
        });

    }

    private void addUser(final User user) {
        waitBar.show();

        Call<User> call = userService.Register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                waitBar.hide();
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                getAuth(user.email,user.password);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAuth(String email, String password) {

        waitBar.show();
        AuthBody body = new AuthBody(email,password);
        Call<Auth> call = userService.Login(body);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(@NotNull Call<Auth> call, @NotNull Response<Auth> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 400)
                        Toast.makeText(RegisterActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RegisterActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                waitBar.hide();
                GetUserWithRole(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<Auth> call, @NotNull Throwable t) {
                waitBar.hide();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, response.code() + "  " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                new Apm(getApplicationContext()).saveSharedInfo(new Shared(response.body(),body));
                new AuthMainPageIntent(response.body(),RegisterActivity.this).Page();


            }

            @Override
            public void onFailure(Call<UserRole> call, Throwable t) {
                waitBar.hide();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
