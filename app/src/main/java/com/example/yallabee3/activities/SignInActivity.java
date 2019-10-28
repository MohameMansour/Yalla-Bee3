package com.example.yallabee3.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
    @BindView(R.id.forget_pass_textView)
    TextView forgetPassTextView;

    private FirebaseAuth mAuth;

    private String email, password;

    private ProgressDialog progressDialog;
    String DeviceLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();
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
        if (DeviceLang.equals("ar")) {
            progressDialog.setMessage("من فضلك انتظر ....");
        } else {
            progressDialog.setMessage("Please Wait ....");
        }
        progressDialog.setMessage("Please Wait ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).
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
                Intent i = new Intent(SignInActivity.this, CountryActivity.class);
                startActivity(i);
                break;
        }
    }

    @OnClick(R.id.forget_pass_textView)
    public void onViewClicked() {
//        startActivity(new Intent(SignInActivity.class , ));
        openGroupDialog();
    }


    public void openGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.dialog_forget_password, null);

        Button sendEmail = dialogView.findViewById(R.id.send_button);
        Button back = dialogView.findViewById(R.id.cancle_button);
        EditText emailtxt = dialogView.findViewById(R.id.email_forget_edittext);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        sendEmail.setOnClickListener(v -> {
            String email = emailtxt.getText().toString();
            if (TextUtils.isEmpty(email)) {
                if (DeviceLang.equals("ar")) {
                    Toast.makeText(this, "من فضلك ادخل الايميل", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            if (DeviceLang.equals("ar")) {
                                Toast.makeText(SignInActivity.this, "افحص الحساب الخاص بك ل تعديل كلمه السر", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignInActivity.this, "Visit Your Email To Reset Password", Toast.LENGTH_SHORT).show();
                            }
//                        Toast.makeText(SignInActivity.this, "Visit Your Email To Reset Password", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(SignInActivity.class , ));}
                            dialog.dismiss();
                        }
                        else{
                            String msg = task.getException().getMessage();
                            Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        back.setOnClickListener(v -> dialog.dismiss());

    }
}
