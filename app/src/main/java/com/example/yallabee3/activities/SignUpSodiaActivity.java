package com.example.yallabee3.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.helpers.InputValidator;
import com.example.yallabee3.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpSodiaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.signe_up_client_img_view)
    CircleImageView signUpClientImgView;
    @BindView(R.id.signupe_name_editText)
    EditText signupNameEditText;
    @BindView(R.id.signupe_email_editText)
    EditText signupEmailEditText;
    @BindView(R.id.signupe_password_editText)
    EditText signupPasswordEditText;
    @BindView(R.id.signupe_repassword_editText)
    EditText signupRepasswordEditText;
    @BindView(R.id.signupe_phone_editText)
    EditText signupPhoneEditText;
    @BindView(R.id.signupe_button)
    Button signupButton;
    @BindView(R.id.user_signin_textView)
    TextView userSigninTextView;
    String DeviceLang;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String text, subCatId;
    FirebaseStorage storage;
    StorageReference storageReference;
    @BindView(R.id.spinner1)
    AppCompatSpinner spinner1;

    private Uri photoUri;
    private String userName, email, password, repassword, phone;
    private ProgressDialog progressDialog;
    private final int PICK_IMAGE_REQUEST = 71;
    String allcountry ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sodia);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();

        Intent i = this.getIntent();
        allcountry = i.getExtras().getString("countryId_key");
//        Toast.makeText(this, "" + allcountry, Toast.LENGTH_SHORT).show();
        if (allcountry.equals("Egypt") || allcountry.equals("مصر")) {
            allcountry = "مصر";
        } else if (allcountry.equals("Arab Emirates") || allcountry.equals("الامارات")) {
            allcountry = "الامارات";
        } else if (allcountry.equals("KSA") || allcountry.equals("السعودية العربية")) {
            allcountry = "السعودية العربية";
        } else {
            allcountry = "الكويت";
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.countrysodia
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner1.setPrompt("Select City");

    }

    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select pic"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            photoUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                signUpClientImgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getInputData() {
        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!InputValidator.signUpValidation(getApplicationContext(), signupNameEditText, signupEmailEditText
                , signupPasswordEditText, signupRepasswordEditText, signupPhoneEditText))
            return false;
        userName = signupNameEditText.getText().toString();
        email = signupEmailEditText.getText().toString().trim();
        password = signupPasswordEditText.getText().toString().trim();
        repassword = signupRepasswordEditText.getText().toString().trim();
        phone = signupPhoneEditText.getText().toString().trim();
        return true;
    }

    private void register(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUser(user, photoUri);
//                            updateUI(user);
                        } else {
                            Toast.makeText(SignUpSodiaActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

//                            updateUI(null);
                        }
                    }
                });
    }


    private void saveUser(final FirebaseUser user, final Uri uri) {
        final String userId = user.getUid();
        String city = text;
        String area = subCatId;
        if (uri == null) {
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();

            User newUser = new User(userId, userName, email, phone, allcountry, city, area);
            databaseReference.child("User").child(userId).setValue(newUser);
            progressDialog.dismiss();
            Intent i = new Intent(SignUpSodiaActivity.this, SignInActivity.class);
            startActivity(i);
            // Missing code
            if (DeviceLang.equals("ar")) {
                Toast.makeText(SignUpSodiaActivity.this, "تهانينا,سجل دخولك الان", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpSodiaActivity.this, "Congratulation,Sign In Now", Toast.LENGTH_SHORT).show();
            }
            finish();

        } else {
        final String imageName = UUID.randomUUID().toString() + ".jpg";


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child("User").child("Images").child(userId).child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("User").child("Images").child(userId + "/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference();
                        String city = text;
                        String area = text;
                        String downUrl = uri.toString();
                        User newUser = new User(userId, userName, email, phone, downUrl, allcountry, city, area);
                        databaseReference.child("User").child(userId).setValue(newUser);
                        // Missing code
//                        Intent i = new Intent(SignUpSodiaActivity.this , SignInActivity.class);
//                        i.putExtra("countryId_key",allcountry);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
                        progressDialog.dismiss();
                        Intent i = new Intent(SignUpSodiaActivity.this, SignInActivity.class);
                        startActivity(i);
                        if (DeviceLang.equals("ar")) {
                            Toast.makeText(SignUpSodiaActivity.this, "تهانينا,سجل دخولك الان", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpSodiaActivity.this, "Congratulation,Sign In Now", Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                });
            }
        });
    }
    }

    @OnClick({R.id.signupe_button, R.id.user_signin_textView, R.id.signe_up_client_img_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signupe_button:
                if (getInputData())
                    register(email, password);
                break;
            case R.id.user_signin_textView:
                Intent i = new Intent(SignUpSodiaActivity.this, SignInActivity.class);
                startActivity(i);
                break;
            case R.id.signe_up_client_img_view:
//                checkAndroidVersion();
                chooseImage();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}