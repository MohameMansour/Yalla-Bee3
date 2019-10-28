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

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.sign_up_client_img_view)
    CircleImageView signUpClientImgView;
    @BindView(R.id.signup_name_editText)
    EditText signupNameEditText;
    @BindView(R.id.signup_email_editText)
    EditText signupEmailEditText;
    @BindView(R.id.signup_password_editText)
    EditText signupPasswordEditText;
    @BindView(R.id.signup_repassword_editText)
    EditText signupRepasswordEditText;
    @BindView(R.id.signup_phone_editText)
    EditText signupPhoneEditText;
    @BindView(R.id.signup_button)
    Button signupButton;
    @BindView(R.id.user_signin_textView)
    TextView userSigninTextView;

    //FireBase Authentication
    FirebaseAuth mAuth;

    //FireBase Database
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String text, subCatId;
    //FireBase Storage
    FirebaseStorage storage;
    StorageReference storageReference;
    @BindView(R.id.spinner)
    AppCompatSpinner spinner;
    @BindView(R.id.spinner_sub)
    AppCompatSpinner spinnerSub;
    String allcountry;
    private Uri photoUri;
    private String userName, email, password, repassword, phone;
    private ProgressDialog progressDialog;
    private final int PICK_IMAGE_REQUEST = 71;
    String DeviceLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();

        Intent i = this.getIntent();
        allcountry = i.getExtras().getString("countryId_key");

        if (allcountry.equals("Egypt") || allcountry.equals("مصر")) {
            allcountry = "مصر";
        } else if (allcountry.equals("Arab Emirates") || allcountry.equals("الامارات")) {
            allcountry = "الامارات";
        } else if (allcountry.equals("KSA") || allcountry.equals("السعودية العربية")) {
            allcountry = "السعودية العربية";
        } else {
            allcountry = "الكويت";
        }
//        if (allcountry.equals("مصر")){
//
//
//        }
//        else {
//
//        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.country
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select City");
    }

//    public void checkAndroidVersion() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            try {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
//            } catch (Exception e) {
//
//            }
//        } else {
//            pickUpImage();
//        }
//    }
//
//    private void pickUpImage() {
//        CropImage.startPickImageActivity(this);
//        Picasso.get()
//                .load(photoUri)
//                .into(signUpClientImgView);
//    }
//
//    private void cropRequest(Uri photoUri) {
//        CropImage.activity(photoUri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setMultiTouchEnabled(true)
//                .start(this);
//    }
//


    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
//        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(i, "select pic"), PICK_IMAGE_REQUEST);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            photoUri = CropImage.getPickImageResultUri(this, data);
////            cropRequest(photoUri);
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
//                    signUpClientImgView.setImageBitmap(bitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            pickUpImage();
//        } else {
//            checkAndroidVersion();
//        }
//    }

    public boolean getInputData() {
//        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
//            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
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
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(i);
            // Missing code
            if (DeviceLang.equals("ar")) {
                Toast.makeText(SignUpActivity.this, "سجل دخولك الان", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Sign In Now", Toast.LENGTH_SHORT).show();
            }
            finish();

        } else {
//            User newUser = new User(userId, userName, email, phone, downUrl, allcountry, city, area);
//            databaseReference.child("User").child(userId).setValue(newUser);


            final String imageName = UUID.randomUUID().toString() + ".jpg";
//        final String userId = user.getUid();

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
                            String area = subCatId;
//                        String downUrl = uri.toString();
                            String downUrl = uri.toString();
//                        if (downUrl.isEmpty()){
//                            User newUser = new User(userId, userName, email, phone, allcountry, city, area);
//                            databaseReference.child("User").child(userId).setValue(newUser);
//
//                        }
//                        else {
//                            User newUser = new User(userId, userName, email, phone, downUrl, allcountry, city, area);
//                            databaseReference.child("User").child(userId).setValue(newUser);
//
//                        }

                            User newUser = new User(userId, userName, email, phone, downUrl, allcountry, city, area);
                            databaseReference.child("User").child(userId).setValue(newUser);
                            progressDialog.dismiss();
                            Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(i);
                            // Missing code
                            if (DeviceLang.equals("ar")) {
                                Toast.makeText(SignUpActivity.this, "تهانينا,سجل دخولك الان", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Congratulation,Sign In Now", Toast.LENGTH_SHORT).show();
                            }
                            finish();

                        }
                    });
                }
            });
        }
    }

    @OnClick({R.id.signup_button, R.id.user_signin_textView, R.id.sign_up_client_img_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                if (getInputData())
                    register(email, password);
                break;
            case R.id.user_signin_textView:
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
                break;
            case R.id.sign_up_client_img_view:
//                checkAndroidVersion();
                chooseImage();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();

        if (position == 0) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.subcountry_kwuit
                            , android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSub.setAdapter(adapter);

        }
        if (position == 1) {
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                    .createFromResource(this, R.array.subcountry_Hawalli
                            , android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSub.setAdapter(adapter1);
        }
        if (position == 2) {
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter
                    .createFromResource(this, R.array.Mubarak_kwuit
                            , android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSub.setAdapter(adapter2);
        }
        if (position == 3) {
            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
                    .createFromResource(this, R.array.Ahmadi_kwuit
                            , android.R.layout.simple_spinner_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSub.setAdapter(adapter3);
        }

        spinnerSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCatId = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
