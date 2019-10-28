package com.example.yallabee3.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.helpers.InputValidator;
import com.example.yallabee3.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.constraint.Constraints.TAG;

public class EditroductActivity extends AppCompatActivity {

    @BindView(R.id.aaddproduct_image_imageView)
    ImageView aaddproductImageImageView;
    @BindView(R.id.aaddproduct_title_edittext)
    EditText aaddproductTitleEdittext;
    @BindView(R.id.display_cate)
    TextView displayCate;
    @BindView(R.id.display_sub_cate)
    TextView displaySubCate;
    @BindView(R.id.aaddproduct_price_edittext)
    EditText aaddproductPriceEdittext;
    @BindView(R.id.aaddproduct_phone_edittext)
    EditText aaddproductPhoneEdittext;
    @BindView(R.id.display_country)
    TextView displayCountry;
    @BindView(R.id.display_sub_country)
    TextView displaySubCountry;
    @BindView(R.id.aaddproduct_desc_edittext)
    EditText aaddproductDescEdittext;
    @BindView(R.id.aaddproduct_button)
    Button aaddproductButton;

    private String title, description, price, phone, place;
    private Uri photoUri;
    private Uri ImageUri;
    private int upload_count = 0;
    String text, subCatId;
    private ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference myRef;
    DatabaseReference databaseReference;
    String productId;
    String subCat;
    String category;
    String subcountry;
    String country;
    String name;
    String imagee;
    String currentcountryfromfirebase;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editroduct);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        Intent i = this.getIntent();
        productId = i.getExtras().getString("productIds_key");
        //
        SharedPreferences sp = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");
        Log.d(TAG, "onBindViewHolder: " + currentcountryfromfirebase);

        if (currentcountryfromfirebase.equals("مصر")) {
            myRef = FirebaseDatabase.getInstance().getReference().child("ProductsEgy").child(productId);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            myRef = FirebaseDatabase.getInstance().getReference().child("ProductsSod").child(productId);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            myRef = FirebaseDatabase.getInstance().getReference().child("ProductsEm").child(productId);
        }else {
            myRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productId);
        }
        //
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                name = product.getTitle();
                imagee = product.getProductImageUrl();
                String desc = product.getDescription();
                String phone = product.getPhone();
                country = product.getPlace();
                subcountry = product.getSubcountry();
                category = product.getCatId();
                subCat = product.getSubCatId();
                String price = product.getPrice();
                if (imagee != null) {
                    Picasso.get()
                            .load(imagee)
                            .into(aaddproductImageImageView);
                }
                aaddproductTitleEdittext.setText(name);
                aaddproductDescEdittext.setText(desc);
                aaddproductPhoneEdittext.setText(phone);
                aaddproductPriceEdittext.setText(price);
                displayCountry.setText(country);
                displaySubCountry.setText(subcountry);
                displayCate.setText(category);
                displaySubCate.setText(subCat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }

    private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
//        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
                aaddproductImageImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //
    public boolean getInputData() {
        if (!InputValidator.addProductValidation(getApplicationContext(), aaddproductTitleEdittext, aaddproductDescEdittext, aaddproductPriceEdittext, aaddproductPhoneEdittext))
            return false;

        title = aaddproductTitleEdittext.getText().toString();
        description = aaddproductDescEdittext.getText().toString().trim();
        price = aaddproductPriceEdittext.getText().toString().trim();
        phone = aaddproductPhoneEdittext.getText().toString().trim();
        return true;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void saveUser(final Uri uri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

//        storageReference.child("Products").child("Images").child(productId).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                storageReference.child("Products").child("Images").child(productId + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        //                    @Override
//                    public void onSuccess(Uri uri) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
//                        String downUrl = uri.toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        String totaltime = null;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat gethour = new SimpleDateFormat("HH");
        SimpleDateFormat getminute = new SimpleDateFormat("mm");
        String hour = gethour.format(c.getTime());
        String minute = getminute.format(c.getTime());
        int convertedVal = Integer.parseInt(hour);

        if (convertedVal > 12) {
            totaltime = ((convertedVal - 12) + ":" + (minute) + "pm");
        } else if (convertedVal == 12) {
            totaltime = ("12" + ":" + (minute) + "pm");
        } else if (convertedVal < 12) {
            if (convertedVal != 0) {
                totaltime = ((convertedVal) + ":" + (minute) + "am");
            } else {
                totaltime = ("12" + ":" + minute + "am");
            }
        }
        //
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        String t_d = date_n + "_" + totaltime;
        //
        Product product = new Product(productId, title, description, price, phone, country, imagee, userId, category, subCat, t_d, subcountry);
        if (currentcountryfromfirebase.equals("مصر")) {
            databaseReference.child("ProductsEgy").child(productId).setValue(product);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            databaseReference.child("ProductsSod").child(productId).setValue(product);
        } else if (currentcountryfromfirebase.equals("الامارات")){
            databaseReference.child("ProductsEm").child(productId).setValue(product);
        } else {
            databaseReference.child("Products").child(productId).setValue(product);
        }
//        databaseReference.child("Products").child(productId).setValue(product);
        // Missing code
        Toast.makeText(EditroductActivity.this, "Added", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
//                        Intent i = new Intent(context, NavActivity.class);
        Intent i = new Intent(EditroductActivity.this, NavActivity.class);
        startActivity(i);
    }

    //                });
//            }
//        });
//    }
    //
    @OnClick({R.id.aaddproduct_image_imageView, R.id.aaddproduct_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aaddproduct_image_imageView:
                break;
            case R.id.aaddproduct_button:
                if (getInputData())
                    saveUser(photoUri);
                break;
        }
    }
}
