package com.example.yallabee3.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.NavActivity;
import com.example.yallabee3.helpers.InputValidator;
import com.example.yallabee3.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
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
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.aaddproduct_image_imageView)
    ImageView productImage;
    @BindView(R.id.aaddproduct_title_edittext)
    EditText titleEditText;
    @BindView(R.id.aaddproduct_desc_edittext)
    EditText descriptionEditText;
    @BindView(R.id.aaddproduct_price_edittext)
    EditText priceEditText;
    @BindView(R.id.aaddproduct_phone_edittext)
    EditText phoneEditText;
    @BindView(R.id.aaddproduct_place_edittext)
    EditText placeEditText;
    @BindView(R.id.aaddproduct_button)
    Button addproductButton;
    Unbinder unbinder;

    private String title, description, price, phone, place;
    private Uri photoUri;

    Context context;
    String text;
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

    private final int PICK_IMAGE_REQUEST = 71;
    Spinner spinner;

    public AddFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        unbinder = ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();
        context = getActivity().getApplicationContext();

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(context, R.array.categories
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select an Category");

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//
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
//        CropImage.startPickImageActivity(getActivity());
//        Picasso.get()
//                .load(photoUri)
//                .into(productImage);
//    }
//
//    private void cropRequest(Uri photoUri) {
//        CropImage.activity(photoUri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setMultiTouchEnabled(true)
//                .start(getActivity());
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
//            photoUri = CropImage.getPickImageResultUri(context, data);
//            cropRequest(photoUri);
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getUri());
//                    productImage.setImageBitmap(bitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            pickUpImage();
//        } else {
//            checkAndroidVersion();
//        }
//    }

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                productImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public boolean getInputData() {
        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
            Toast.makeText(context, "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!InputValidator.addProductValidation(context.getApplicationContext(), titleEditText, descriptionEditText, priceEditText, phoneEditText, placeEditText))
            return false;

        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString().trim();
        price = priceEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        place = placeEditText.getText().toString().trim();
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

    private void saveUser(final Uri uri, final String text) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait ....");
//        progressDialog.show();

        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();


        final String imageName = UUID.randomUUID().toString() + ".jpg";
//        final String Id = databaseReference.push().getKey();
//        final String userId = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference.child("Products").child("Images").child(Id).child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child("Products").child("Images").child(Id + "/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference();
                        String downUrl = uri.toString();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        String catId = text;

                        Product product = new Product(Id, title, description, price, phone, place, downUrl, userId, catId);
                        databaseReference.child("Products").child(Id).setValue(product);
                        // Missing code
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, NavActivity.class);
                        startActivity(i);

                    }
                });
            }
        });
    }


    @OnClick({R.id.aaddproduct_image_imageView, R.id.aaddproduct_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aaddproduct_image_imageView:
//                checkAndroidVersion();
                chooseImage();
                break;
            case R.id.aaddproduct_button:
                if (getInputData())
//                    register(email, password);
                    saveUser(photoUri, text);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
//        DatabaseReference db_ref_Main = fb_db_instance.getReference();
//        DatabaseReference blankRecordReference = db_ref_Main;
//        DatabaseReference db_ref = blankRecordReference.push();
//        String Id = db_ref.getKey();

        text = parent.getItemAtPosition(position).toString();
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference();
//
//        databaseReference.child("Product").child(Id).setValue(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
