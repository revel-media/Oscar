package com.krito.io.oscar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.krito.io.oscar.R;
import com.krito.io.oscar.model.data.Constants;
import com.krito.io.oscar.model.operations.UserOperation;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, UserOperation.LoginCallback {

    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button forgetPasswordButton;
    TextView dontHaveAccountView;
    TextView errorView;
    ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextInputLayout = findViewById(R.id.emailTextLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextLayout);
        emailEditText = findViewById(R.id.emailView);
        passwordEditText = findViewById(R.id.passwordView);
        loginButton = findViewById(R.id.loginButton);
        forgetPasswordButton = findViewById(R.id.forgetPassButton);
        dontHaveAccountView = findViewById(R.id.dontHaveAccountView);
        errorView = findViewById(R.id.loginResultView);
        progressView = findViewById(R.id.loginProgress);

        dontHaveAccountView.setPaintFlags(dontHaveAccountView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(this);
        forgetPasswordButton.setOnClickListener(this);
        dontHaveAccountView.setOnClickListener(this);
        UserOperation.setLoginCallback(this);
    }


    private boolean validEmail(){
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailTextInputLayout.setError(getString(R.string.email_input_error));
            requestFocus(emailEditText);
            progressView.setVisibility(View.GONE);
            return false;
        } else {
            emailTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validPassword(){
        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordTextInputLayout.setError(getString(R.string.password_input_error));
            requestFocus(passwordEditText);
            progressView.setVisibility(View.GONE);
            return false;
        } else {
            passwordTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:

                progressView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                if (!validEmail()){
                    return;
                }
                if (!validPassword()){
                    return;
                }

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                UserOperation.login(email, password);

                break;
            case R.id.dontHaveAccountView:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forgetPassButton:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject object) {
        try {
            if (object.getBoolean("error")){
                errorView.setText(object.getString("message"));
                errorView.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
            } else{
                SharedPreferences.Editor editor = LoginActivity.this.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(Constants.SHARED_SIGNED_IN, true).apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VolleyError error) {
        errorView.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.GONE);
    }
}
