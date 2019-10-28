package com.example.yallabee3.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.SelectPackageActivity;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

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
    //    @BindView(R.id.aaddproduct_place_edittext)
//    EditText placeEditText;
    @BindView(R.id.aaddproduct_button)
    Button addproductButton;
    Unbinder unbinder;
    @BindView(R.id.spinner_coun)
    AppCompatSpinner spinnerCoun;
    @BindView(R.id.spinner_subcou)
    AppCompatSpinner spinnerSubcou;

    String country, subcountry;
    private String title, description, price, phone, place;
    private Uri photoUri;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri ImageUri;
    private int upload_count = 0;
    Context context;
    String text, subCatId;
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
    AppCompatSpinner spinner, spinnersub;
    String currentcountryfromfirebase;

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
        spinnersub = view.findViewById(R.id.spinner_sub);

        SharedPreferences sp = context.getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");
//        if(currentcountryfromfirebase.equals("مصر")){
//        }
//        else{
//        }

        ArrayAdapter<CharSequence> adaptercountry = ArrayAdapter
                .createFromResource(context, R.array.country
                        , android.R.layout.simple_spinner_item);
        adaptercountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoun.setAdapter(adaptercountry);
        spinnerCoun.setOnItemSelectedListener(this);
        spinnerCoun.setPrompt("Select Country");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(context, R.array.categories
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select an Category");


        spinnerCoun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = parent.getItemAtPosition(position).toString();
                //
                //
                if (position == 0) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter
                            .createFromResource(context, R.array.subcountry_kwuit
                                    , android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubcou.setAdapter(adapter);

                }
                if (position == 1) {
                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                            .createFromResource(context, R.array.subcountry_Hawalli
                                    , android.R.layout.simple_spinner_item);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubcou.setAdapter(adapter1);
                }
                if (position == 2) {
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter
                            .createFromResource(context, R.array.Mubarak_kwuit
                                    , android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubcou.setAdapter(adapter2);
                }
                if (position == 3) {
                    ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
                            .createFromResource(context, R.array.Ahmadi_kwuit
                                    , android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubcou.setAdapter(adapter3);
                }

                spinnerSubcou.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subcountry = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //
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

    //
    private void pickUpImage() {
        CropImage.startPickImageActivity(getActivity());
        Picasso.get()
                .load(photoUri)
                .into(productImage);
    }

    private void cropRequest(Uri photoUri) {
        CropImage.activity(photoUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getActivity());
    }

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                productImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            if (data.getClipData() != null) {
//                int countClipData = data.getClipData().getItemCount();
//                int currentImageSelected = 0;
//                while (currentImageSelected < countClipData) {
//                    ImageUri = data.getClipData().getItemAt(currentImageSelected).getUri();
//                    ImageList.add(ImageUri);
//                    currentImageSelected = currentImageSelected + 1;
//                }
//                Toast.makeText(context, "You Select" + ImageList.size() + "Images", Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(context, "select 4 images", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    public boolean getInputData() {
        if (photoUri == null || Uri.EMPTY.equals(photoUri)) {
            Toast.makeText(context, "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!InputValidator.addProductValidation(context.getApplicationContext(), titleEditText, descriptionEditText, priceEditText, phoneEditText))
            return false;

        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString().trim();
        price = priceEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
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

    private void saveUser(final Uri uri, final String text, final String subCatId) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait ....");
//        progressDialog.setCancelable(false);
        progressDialog.show();


        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();


//     true   final String imageName = UUID.randomUUID().toString() + ".jpg";
//        final String Id = databaseReference.push().getKey();
//        final String userId = user.getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
//  true      storageReference.child("Products").child("Images").child(Id).child(imageName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        storageReference.child("Products").child("Images").child(Id).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//   true             storageReference.child("Products").child("Images").child(Id + "/" + imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                storageReference.child("Products").child("Images").child(Id + "/").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(Uri uri) {
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference();
                        String downUrl = uri.toString();
                        String downUrl2 = uri.toString();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        String catId = text;
                        String subsCatId = subCatId;
                        String place = country;
                        String subplace = subcountry;
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
                        Product product = new Product(Id, title, description, price, phone, place, downUrl, userId, catId, subsCatId, t_d, subplace);


                        if (currentcountryfromfirebase.equals("مصر")) {
                            databaseReference.child("ProductsEgy").child(Id).setValue(product);
                        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                            databaseReference.child("ProductsSod").child(Id).setValue(product);
                        } else if (currentcountryfromfirebase.equals("الامارات")) {
                            databaseReference.child("ProductsEm").child(Id).setValue(product);
                        } else {
                            databaseReference.child("Products").child(Id).setValue(product);
                        }

//                        databaseReference.child("Products").child(Id).setValue(product);
                        // Missing code
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).

                                show();
                        progressDialog.dismiss();
                        //                        Intent i = new Intent(context, NavActivity.class);
                        Intent i = new Intent(context, SelectPackageActivity.class);

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
                    saveUser(photoUri, text, subCatId);

//                storage = FirebaseStorage.getInstance();
//                storageReference = storage.getReference();
//                storageReference.child("Products").child("Images");
//                for(upload_count = 0 ;upload_count < ImageList.size() ;upload_count++){
//
//                }
//
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


        //
        String arab;
        if (text.equals("عربيات وقطع غيار")) {
            text = "Vehicles";
        } else if (text.equals("عقارات")) {
            text = "Properties";
        } else if (text.equals("موبايلات واكسسواراتها")) {
            text = "Mobile Phones, Accessories";
        } else if (text.equals("الكترونيات وأجهزة منزلية")) {
            text = "Electronics, Home Appliances";
        } else if (text.equals("المنزل والحديقة")) {
            text = "Home, Garden";
        } else if (text.equals("الموضة والجمال")) {
            text = "Fashion, Beauty";
        } else if (text.equals("حيوانات أليفة")) {
            text = "Pets";
        } else if (text.equals("مستلزمات أطفال")) {
            text = "Kids, Babies";
        } else if (text.equals("دراجات ومعدات رياضية")) {
            text = "Sporting Goods, Bikes";
        } else if (text.equals("موسيقى وفنون وكتب")) {
            text = "Hobbies, Music, Art, Books";
        } else if (text.equals("وظائف")) {
            text = "Jobs";
        } else if (text.equals("تجارة وصناعة")) {
            text = "Business, Industrial";
        } else if (text.equals("خدمات")) {
            text = "Services";
        }

        if (position == 0) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_vehicles
                            , android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter);

        }
        if (position == 1) {
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Properties
                            , android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter1);
        }
        if (position == 2) {
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_mobilephone_accessories
                            , android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter2);
        }
        if (position == 3) {
            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Electronics_HomeAppliances
                            , android.R.layout.simple_spinner_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter3);
        }
        if (position == 4) {
            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Home_Garden
                            , android.R.layout.simple_spinner_item);
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter4);
        }
        if (position == 5) {
            ArrayAdapter<CharSequence> adapter5 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Fashion_Beauty
                            , android.R.layout.simple_spinner_item);
            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter5);
        }
        if (position == 6) {
            ArrayAdapter<CharSequence> adapter6 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Pets
                            , android.R.layout.simple_spinner_item);
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter6);
        }
        if (position == 7) {
            ArrayAdapter<CharSequence> adapter7 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Kids_Babies
                            , android.R.layout.simple_spinner_item);
            adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter7);
        }
        if (position == 8) {
            ArrayAdapter<CharSequence> adapter8 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Sporting_Goods_Bikes
                            , android.R.layout.simple_spinner_item);
            adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter8);
        }
        if (position == 9) {
            ArrayAdapter<CharSequence> adapter9 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Hobbies_Music_Art_Books
                            , android.R.layout.simple_spinner_item);
            adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter9);
        }
        if (position == 10) {
            ArrayAdapter<CharSequence> adapter10 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Jobs
                            , android.R.layout.simple_spinner_item);
            adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter10);
        }
        if (position == 11) {
            ArrayAdapter<CharSequence> adapter11 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Business_Industrial
                            , android.R.layout.simple_spinner_item);
            adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter11);
        }
        if (position == 12) {
            ArrayAdapter<CharSequence> adapter12 = ArrayAdapter
                    .createFromResource(context, R.array.subcategories_Services
                            , android.R.layout.simple_spinner_item);
            adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnersub.setAdapter(adapter12);
        }

        spinnersub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCatId = parent.getItemAtPosition(position).toString();
                if (subCatId.equals("قوارب")) {
                    subCatId = "Boats";
                } else if (subCatId.equals("اكسسوارات عربيات")) {
                    subCatId = "Car Accessories";
                } else if (subCatId.equals("عربيات")) {
                    subCatId = "Cars";
                } else if (subCatId.equals("موتوسيكلات")) {
                    subCatId = "Motorcycles";
                } else if (subCatId.equals("وسائل نقل أخرى")) {
                    subCatId = "Other Vehicles";
                } else if (subCatId.equals("يخوت")) {
                    subCatId = "Spare parts";
                } else if (subCatId.equals("اتوبيسات وعربيات نقل")) {
                    subCatId = "Trucks, buses";
                } else if (subCatId.equals("عقارات للإيجار")) {
                    subCatId = "Properties for Rent";
                } else if (subCatId.equals("عقارات للبيع")) {
                    subCatId = "Properties for Sale";
                } else if (subCatId.equals("موبايلات")) {
                    subCatId = "Mobile Phones";
                } else if (subCatId.equals("إكسسوارات موبايلات")) {
                    subCatId = "Mobile Accessories";
                } else if (subCatId.equals("تلفزيونات وصوتيات")) {
                    subCatId = "TV - Audio - Video";
                } else if (subCatId.equals("كمبيوتر وتابلت")) {
                    subCatId = "Computers - Tablets";
                } else if (subCatId.equals("أجهزة وألعاب فيديو")) {
                    subCatId = "Video games - Consoles";
                } else if (subCatId.equals("كاميرات وتصوير")) {
                    subCatId = "Cameras - Imaging";
                } else if (subCatId.equals("أجهزة منزلية")) {
                    subCatId = "Home Appliances";
                } else if (subCatId.equals("اكسسوارات وزينة")) {
                    subCatId = "Decoration - Accessories";
                } else if (subCatId.equals("أثاث")) {
                    subCatId = "Furniture";
                } else if (subCatId.equals("الحدائق والمناطق الخارجية")) {
                    subCatId = "Garden - Outdoor";
                } else if (subCatId.equals("معدات وأدوات طبخ")) {
                    subCatId = "Kitchenware";
                } else if (subCatId.equals("منزل وحديقة أخرى")) {
                    subCatId = "Other Home - Garden";
                } else if (subCatId.equals("ملابس واكسسوارات")) {
                    subCatId = "Clothing - Accessories";
                } else if (subCatId.equals("ساعات ومجوهرات")) {
                    subCatId = "Jewelry - Watches";
                } else if (subCatId.equals("صحة وجمال")) {
                    subCatId = "Health - Beauty - Cosmetics";
                } else if (subCatId.equals("قطط")) {
                    subCatId = "Cats";
                } else if (subCatId.equals("كلاب")) {
                    subCatId = "Dogs";
                } else if (subCatId.equals("خيل")) {
                    subCatId = "Horses";
                } else if (subCatId.equals("اسماك")) {
                    subCatId = "Fishes";
                } else if (subCatId.equals("طيور")) {
                    subCatId = "Birds";
                } else if (subCatId.equals("حيوانات اليفه اخرى")) {
                    subCatId = "Other Pets";
                } else if (subCatId.equals("ملابس الاطفال والرضع")) {
                    subCatId = "Clothes for Kids and Babies";
                } else if (subCatId.equals("لعب اطفال")) {
                    subCatId = "Toys";
                } else if (subCatId.equals("سراير وعربات أطفال")) {
                    subCatId = "Cribs - Strollers";
                } else if (subCatId.equals("اكسسوارات")) {
                    subCatId = "Accessories";
                } else if (subCatId.equals("مستلزمات أطفال أخرى")) {
                    subCatId = "Other Kids - Babies";
                } else if (subCatId.equals("معدات رياضية")) {
                    subCatId = "Sporting Goods";
                } else if (subCatId.equals("دراجات")) {
                    subCatId = "Bikes";
                } else if (subCatId.equals("معدات رياضية خارجية")) {
                    subCatId = "Outdoor Equipment";
                } else if (subCatId.equals("تحف ومقتنيات")) {
                    subCatId = "Antiques - Collectibles";
                } else if (subCatId.equals("كتب")) {
                    subCatId = "Books";
                } else if (subCatId.equals("أفلام وموسيقى")) {
                    subCatId = "Movies - Music";
                } else if (subCatId.equals("آلات موسيقية")) {
                    subCatId = "Musical instruments";
                } else if (subCatId.equals("اغراض اخرى")) {
                    subCatId = "Other items";
                } else if (subCatId.equals("ادوات دراسية")) {
                    subCatId = "Study tools";
                } else if (subCatId.equals("تذاكر وقسائم")) {
                    subCatId = "Tickets - Vouchers";
                } else if (subCatId.equals("محاسبه")) {
                    subCatId = "Accounting";
                } else if (subCatId.equals("عمارة - هندسة")) {
                    subCatId = "Architecture - Engineering";
                } else if (subCatId.equals("فنون - تصميم")) {
                    subCatId = "Art - Design";
                } else if (subCatId.equals("تطوير الاعمال")) {
                    subCatId = "Business Development";
                } else if (subCatId.equals("إنشاءات")) {
                    subCatId = "Construction";
                } else if (subCatId.equals("اعمال استشاريه")) {
                    subCatId = "Consulting";
                } else if (subCatId.equals("تعليم")) {
                    subCatId = "Education";
                } else if (subCatId.equals("اعمال اداريه")) {
                    subCatId = "Executive";
                } else if (subCatId.equals("توظيف - موارد بشرية")) {
                    subCatId = "HR - Recruiting";
                } else if (subCatId.equals("سياحة وفنادق وضيافة")) {
                    subCatId = "Hospitality";
                } else if (subCatId.equals("تكنولوجيا المعلومات - إتصالات")) {
                    subCatId = "IT - Telecom";
                } else if (subCatId.equals("يبحث عن عمل")) {
                    subCatId = "Jobs Wanted";
                } else if (subCatId.equals("تسويق - علاقات عامة")) {
                    subCatId = "Marketing - PR";
                } else if (subCatId.equals("طب - صحه")) {
                    subCatId = "Medical - Health";
                } else if (subCatId.equals("اخرى")) {
                    subCatId = "Other";
                } else if (subCatId.equals("تجزئه")) {
                    subCatId = "Retail";
                } else if (subCatId.equals("مبيعات")) {
                    subCatId = "Sales";
                } else if (subCatId.equals("سكرتاريه")) {
                    subCatId = "Secretarial";
                } else if (subCatId.equals("معدات مصانع")) {
                    subCatId = "Factories Equipment";
                } else if (subCatId.equals("معدات طبيه")) {
                    subCatId = "Medical Equipment";
                } else if (subCatId.equals("معدات ثقيله")) {
                    subCatId = "Heavy Equipment";
                } else if (subCatId.equals("مستلزمات مطاعم")) {
                    subCatId = "Restaurants Equipment";
                } else if (subCatId.equals("تصفيه محلات")) {
                    subCatId = "Shops Liquidation";
                } else if (subCatId.equals("تجاره و صناعه اخرى")) {
                    subCatId = "Other Business - Industrial";
                } else if (subCatId.equals("خدمات شركات")) {
                    subCatId = "Business Services";
                } else if (subCatId.equals("تصليح سيارات")) {
                    subCatId = "Car Repairs";
                } else if (subCatId.equals("خدمات منزليه")) {
                    subCatId = "Domestic Services";
                } else if (subCatId.equals("حفلات")) {
                    subCatId = "Events";
                } else if (subCatId.equals("خدمات محليه")) {
                    subCatId = "Home";
                } else if (subCatId.equals("توصيل و شحن")) {
                    subCatId = "Movers";
                } else if (subCatId.equals("خدمات اخرى")) {
                    subCatId = "Other Services";
                } else if (subCatId.equals("خدمات شخصيه")) {
                    subCatId = "Personal Services";
                } else if (subCatId.equals("حيوانات اليفه")) {
                    subCatId = "Pets";
                } else if (subCatId.equals("دروس خصوصيه")) {
                    subCatId = "Private Tutors";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//
        spinnersub.setPrompt("Select an Sub Category");


//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference();
//
//        databaseReference.child("Product").child(Id).setValue(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
