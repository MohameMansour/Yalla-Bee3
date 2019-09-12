package com.example.yallabee3.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.helpers.InputValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.user_email_editText)
    EditText userEmailEditText;
    @BindView(R.id.user_password_editText)
    EditText userPasswordEditText;
    @BindView(R.id.user_login_button)
    Button userLoginButton;
    @BindView(R.id.user_signup_textView)
    TextView userSignupTextView;

    private FirebaseAuth mAuth;

    private String email, password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private boolean getInputData() {
        if (!InputValidator.signInValidation(getApplicationContext(),
                userEmailEditText, userPasswordEditText))
            return false;

        email = userEmailEditText.getText().toString().trim();
        password = userPasswordEditText.getText().toString().trim();
        return true;
    }

    private void signIn(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent i;
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignInActivity.this,
                                            "LogIn Success", Toast.LENGTH_SHORT).show();
                                    i = new Intent(SignInActivity.this, NavActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, task.getException()
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
    }

    @OnClick({R.id.user_login_button, R.id.user_signup_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_login_button:
                if (getInputData())
                    signIn(email, password);
                break;
            case R.id.user_signup_textView:
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
                break;
        }
    }
}
