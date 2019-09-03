package com.example.yallabee3.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.addproduct_image_imageView)
    ImageView productImage;
    @BindView(R.id.addproduct_title_edittext)
    EditText titleEditText;
    @BindView(R.id.addproduct_desc_edittext)
    EditText descriptionEditText;
    @BindView(R.id.addproduct_price_edittext)
    EditText priceEditText;
    @BindView(R.id.addproduct_email_edittext)
    EditText emailEditText;
    @BindView(R.id.addproduct_password_edittext)
    EditText passwordEditText;
    @BindView(R.id.addproduct_button)
    Button addProductButton;


    private String title, description, price, email, password;
    private Uri photoUri;

    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;

//    FireBase Authentication
    FirebaseAuth mAuth;
    //FireBase Database
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    //FireBase Storage
    FirebaseStorage storage;
    StorageReference storageReference;

//    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    //    private void chosseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "selec picture"), PICK_IMAGE_REQUEST);
//    }

    public void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch (Exception e) {

            }
        } else {
            pickUpImage();
        }
    }

    private void pickUpImage() {
        CropImage.startPickImageActivity(this);
        Picasso.get()
                .load(photoUri)
                .into(productImage);
    }

    private void cropRequest(Uri photoUri) {
        CropImage.activity(photoUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            photoUri = CropImage.getPickImageResultUri(this, data);
            cropRequest(photoUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    productImage.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickUpImage();
        } else {
            checkAndroidVersion();
        }
    }

    public boolean getInputData() {
        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (!InputValidator.addProductValidation(getApplicationContext(), titleEditText, descriptionEditText, priceEditText))
//            return false;

        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString().trim();
        price = priceEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        return true;
    }


//    private void register(String email, String password) {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait ....");
//        progressDialog.show();
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    progressDialog.dismiss();
//                    FirebaseUser user = task.getResult().getUser();
//                    saveUser(user, photoUri);
//                } else {
//                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//
//                }
//            }
//        });
//
//    }

    private void saveUser( final Uri uri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();

        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main ;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();


        final String imageName = UUID.randomUUID().toString() + ".jpg";
//        final String Id = databaseReference.push().getKey();
//        final String userId = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child("Product").child("Images").child(Id).child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("Product").child("Images").child(Id + "/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference();
                        String downUrl = uri.toString();
//                        Product product = new Product(Id, title, description, price, email,downUrl);
//                        databaseReference.child("Product").child(Id).setValue(product);
                        // Missing code
                        Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this , ShowActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });
    }

    @OnClick({R.id.addproduct_image_imageView, R.id.addproduct_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addproduct_image_imageView:
                checkAndroidVersion();
                break;
            case R.id.addproduct_button:
                if (getInputData())
//                    register(email, password);
                    saveUser(photoUri);
                break;
        }
    }
}

