package com.example.yallabee3.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.SignInActivity;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Favourite;
import com.example.yallabee3.model.Product;
import com.example.yallabee3.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Context context;
    @BindView(R.id.profile_image_imageView)
    ImageView profileImageImageView;
    @BindView(R.id.profile_username_textview)
    TextView profileUsernameTextview;
    @BindView(R.id.profile_email_textview)
    TextView profileEmailTextview;
    @BindView(R.id.profile_logout_button)
    TextView profileLogoutButton;
    @BindView(R.id.profile_myproducts_textview)
    TextView profileMyproductsTextview;
    @BindView(R.id.profile_products_recyclerView)
    RecyclerView profileProductsRecyclerView;
    @BindView(R.id.profile_myfavourite_textview)
    TextView profileMyfavouriteTextview;
    @BindView(R.id.profile_favourites_recyclerView)
    RecyclerView profileFavouritesRecyclerView;

    //    RecyclerView recyclerView;
//    RecyclerView recyclerView2;
    String currentcountryfromfirebase;
    Unbinder unbinder;

    DatabaseReference myRef;
    DatabaseReference myRef2;

    FirebaseAuth auth;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;
    List<Favourite> favourites = new ArrayList<>();
    ShowAdapter adapter2;
    List<Product> products2 = new ArrayList<>();
    @BindView(R.id.spinner_coun)
    AppCompatSpinner spinnerCoun;
    String country;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        myRef2 = FirebaseDatabase.getInstance().getReference();

//        recyclerView = view.findViewById(R.id.profile_products_recyclerView);
//        recyclerView2 = view.findViewById(R.id.profile_favourites_recyclerView);

        unbinder = ButterKnife.bind(this, view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sp2 = getActivity().getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp2.getString("countryfromdatabase", "new");


        ArrayAdapter<CharSequence> adaptercountry = ArrayAdapter
                .createFromResource(getActivity(), R.array.country33
                        , android.R.layout.simple_spinner_item);
        adaptercountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoun.setAdapter(adaptercountry);
        spinnerCoun.setOnItemSelectedListener(this);
        spinnerCoun.setPrompt("Select Country");


        //
//        profileProductsRecyclerView.setVisibility(View.GONE);
//        profileFavouritesRecyclerView.setVisibility(View.GONE);

        //

        if (user != null) {
            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
            String email = user.getEmail();
//            String photoUrl = userr.getImgUrl();

            myRef.child("User").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);
                    String name = user1.getFullName();
                    String image = user1.getImgUrl();

                    if (image != null) {
                        Picasso.get()
                                .load(image)
                                .into(profileImageImageView);
                    } else {

                    }
                    profileUsernameTextview.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            profileEmailTextview.setText(email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        }

        return view;
    }

    public void getProduct() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("userId").equalTo(user.getUid());
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("userId").equalTo(user.getUid());
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("userId").equalTo(user.getUid());
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(user.getUid());
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products2.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    products2.add(product);
                    adapter2.notifyDataSetChanged();
                }

//                Collections.reverse(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        profileProductsRecyclerView.setHasFixedSize(true);
        adapter2 = new ShowAdapter(products2, getContext(), ShowAdapter.LAND_SCAPE_PRODUCT);
        profileProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileProductsRecyclerView.setAdapter(adapter2);

    }

    public void getFavourite() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("Favouritee").child(user.getUid());
//        Query query2 = FirebaseDatabase.getInstance().getReference("Favouritee").child(user.getUid());
        if (myRef2 != null) {
            myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        products.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Product product = snapshot.getValue(Product.class);
//                    String id = snapshot.getKey();
                            Favourite favourite = snapshot.getValue(Favourite.class);
                            String id = snapshot.getKey();
                            Log.d(TAG, "onDataChang: " + id);
                            assert favourite != null;
                            favourite.setProductId(id);
//                    favourite.setUserId(user.getUid());
//                    favourite.getUserId();
                            favourite.getProductId();
//                    DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
//                    myRef2.child("Favourite").child(id).child("userId");

//                    assert product != null;
//                    product.setId(id);
//                    products.add(product);
//                    adapter.notifyDataSetChanged();
                            String productId = favourite.getProductId();
                            Log.d(TAG, "onDataChangee: " + productId);
                            String userId = favourite.getUserId();
                            Log.d(TAG, "onDataChangeee: " + userId);
                            Log.d(TAG, "onDataChange: " + productId);
                            if (userId.equals(user.getUid())) {
                                DatabaseReference myRef;
                                if (currentcountryfromfirebase.equals("مصر")) {
                                    myRef = FirebaseDatabase.getInstance().getReference("ProductsEgy").child(productId);
                                } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                                    myRef = FirebaseDatabase.getInstance().getReference("ProductsSod").child(productId);
                                } else if (currentcountryfromfirebase.equals("الامارات")) {
                                    myRef = FirebaseDatabase.getInstance().getReference("ProductsEm").child(productId);
                                } else {
                                    myRef = FirebaseDatabase.getInstance().getReference("Products").child(productId);
                                }
//                            myRef.child("Products").child(productId);
                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Product product = dataSnapshot.getValue(Product.class);
                                        products.add(product);
                                        adapter.notifyDataSetChanged();
//                            }
//                                            Collections.reverse(products);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                profileFavouritesRecyclerView.setHasFixedSize(true);
                                adapter = new ShowAdapter(products, getContext(), ShowAdapter.LAND_SCAPE_PRODUCT);
                                profileFavouritesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                                profileFavouritesRecyclerView.setAdapter(adapter);

                            } else {

                            }
                        }

                    } else {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.profile_myproducts_textview, R.id.profile_myfavourite_textview, R.id.profile_logout_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_myproducts_textview:
                if (profileProductsRecyclerView.getVisibility() == View.GONE && products2 == null) {
                    getProduct();
                    profileProductsRecyclerView.setVisibility(View.VISIBLE);
                } else if (profileProductsRecyclerView.getVisibility() == View.VISIBLE) {
                    profileProductsRecyclerView.setVisibility(View.GONE);
                } else {
                    profileProductsRecyclerView.setVisibility(View.VISIBLE);
                    getProduct();
                }
//                getProduct();
//                profileProductsRecyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.profile_myfavourite_textview:
                if (profileFavouritesRecyclerView.getVisibility() == View.GONE && products == null) {
                    getFavourite();
                    profileFavouritesRecyclerView.setVisibility(View.VISIBLE);
                } else if (profileFavouritesRecyclerView.getVisibility() == View.VISIBLE) {
                    profileFavouritesRecyclerView.setVisibility(View.GONE);
                } else {
                    profileFavouritesRecyclerView.setVisibility(View.VISIBLE);
                    getFavourite();
                }
//                getFavourite();
//                profileFavouritesRecyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.profile_logout_button:
                FirebaseAuth.getInstance().signOut();
//      startActivity(new Intent(context, SignInActivity.class)); //Go back to home page
                Intent i = new Intent(getActivity(), SignInActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        country = parent.getItemAtPosition(position).toString();
        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String isd = user.getUid();
        if (position == 0) {
        }
        if (position == 1) {
            country = "الكويت";

            myRef.child("User").child(isd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);

                    String name = user1.getFullName();
                    String imagee = user1.getImgUrl();
                    String email = user1.getEmail();
                    String phone = user1.getPhone();
//                    String country = user1.getCountry();
                    String subcountry = user1.getSubCountry();
                    String area = user1.getArea();
                    if(imagee == null){
                        User newUser = new User(isd, name, email, phone, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);
                    }
                    else {
                        User newUser = new User(isd, name, email, phone, imagee, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);

                    }

//                    Log.d(TAG, "onItemSelected: " + us.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (position == 2) {
            country = "مصر";

            myRef.child("User").child(isd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);

                    String name = user1.getFullName();
                    String imagee = user1.getImgUrl();
                    String email = user1.getEmail();
                    String phone = user1.getPhone();
//                    String country = user1.getCountry();
                    String subcountry = user1.getSubCountry();
                    String area = user1.getArea();
                    if(imagee == null){
                        User newUser = new User(isd, name, email, phone, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);
                    }
                    else {
                        User newUser = new User(isd, name, email, phone, imagee, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);

                    }

//                    Log.d(TAG, "onItemSelected: " + us.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (position == 3) {
            country = "السعودية العربية";
            myRef.child("User").child(isd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);

                    String name = user1.getFullName();
                    String imagee = user1.getImgUrl();
                    String email = user1.getEmail();
                    String phone = user1.getPhone();
//                    String country = user1.getCountry();
                    String subcountry = user1.getSubCountry();
                    String area = user1.getArea();
                    if(imagee == null){
                        User newUser = new User(isd, name, email, phone, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);
                    }
                    else {
                        User newUser = new User(isd, name, email, phone, imagee, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);

                    }

//                    Log.d(TAG, "onItemSelected: " + us.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (position == 4) {
            country = "الامارات";
            myRef.child("User").child(isd).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);

                    String name = user1.getFullName();
                    String imagee = user1.getImgUrl();
                    String email = user1.getEmail();
                    String phone = user1.getPhone();
//                    String country = user1.getCountry();
                    String subcountry = user1.getSubCountry();
                    String area = user1.getArea();
                    if(imagee == null){
                        User newUser = new User(isd, name, email, phone, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);
                    }
                    else {
                        User newUser = new User(isd, name, email, phone, imagee, country, subcountry, area);
                        myRef2.child("User").child(isd).setValue(newUser);
//                        Intent i = new Intent(context, NavActivity.class);
//                        startActivity(i);

                    }

//                    Log.d(TAG, "onItemSelected: " + us.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
