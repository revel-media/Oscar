package com.krito.io.oscar.activities;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.krito.io.oscar.R;
import com.krito.io.oscar.model.data.User;
import com.krito.io.oscar.model.operations.UserOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements UserOperation.RegistrationCallback {

    TextInputLayout nameTextInputLayout;
    TextInputLayout addressTextInputLayout;
    TextInputLayout mobileTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    TextInputLayout rePasswordTextInputLayout;
    EditText nameEditText;
    EditText addressEditText;
    EditText mobileEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText rePasswordEditText;
    Button registerButton;
    TextView haveAccountView;
    TextView errorView;
    ProgressBar progressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameTextInputLayout = findViewById(R.id.nameTextLayout);
        addressTextInputLayout = findViewById(R.id.addressTextLayout);
        mobileTextInputLayout = findViewById(R.id.mobileTextLayout);
        emailTextInputLayout = findViewById(R.id.emailTextLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextLayout);
        rePasswordTextInputLayout = findViewById(R.id.rePasswordTextLayout);

        nameTextInputLayout = findViewById(R.id.nameTextLayout);
        nameEditText = findViewById(R.id.nameView);
        addressEditText = findViewById(R.id.addressView);
        mobileEditText = findViewById(R.id.mobileView);
        emailEditText = findViewById(R.id.emailView);
        passwordEditText = findViewById(R.id.passwordView);
        rePasswordEditText = findViewById(R.id.rePasswordView);
        registerButton = findViewById(R.id.registerButton);
        haveAccountView = findViewById(R.id.haveAccountView);
        errorView = findViewById(R.id.registerResultView);
        progressView = findViewById(R.id.registerProgress);

        haveAccountView.setPaintFlags(haveAccountView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        UserOperation.setRegistrationCallback(this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressView.setVisibility(View.VISIBLE);

                if (!validName()) {
                    return;
                }
                if (!validEmail()) {
                    return;
                }
                if (!validPhone()) {
                    return;
                }
                if (!validPassword()) {
                    return;
                }
                if (!matchPassword()) {
                    return;
                }

                final String name = nameEditText.getText().toString();
                final String address = addressEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String phone = mobileEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final User user = new User();
                user.setName(name);
                user.setAddress(address);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setPassword(password);

                UserOperation.register(user);
            }
        });

        haveAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validName(){
        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameTextInputLayout.setError(getString(R.string.name_input_error));
            requestFocus(nameEditText);
            return false;
        } else {
            nameTextInputLayout.setErrorEnabled(false);
        }

        return true;
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

    private boolean validPhone(){
        if (mobileEditText.getText().toString().trim().isEmpty()) {
            mobileTextInputLayout.setError(getString(R.string.mobile_input_error));
            requestFocus(mobileEditText);
            progressView.setVisibility(View.GONE);
            return false;
        } else {
            mobileTextInputLayout.setErrorEnabled(false);
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

    private boolean matchPassword(){
        if (!passwordEditText.getText().toString().trim().
                contentEquals(rePasswordEditText.getText().toString().trim())) {
            rePasswordTextInputLayout.setError(getString(R.string.password_input_error));
            requestFocus(rePasswordEditText);
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
    public void onResponse(JSONObject object) {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(VolleyError error) {
       errorView.setVisibility(View.VISIBLE);
       progressView.setVisibility(View.GONE);
    }
}
