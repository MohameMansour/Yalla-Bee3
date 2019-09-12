package com.example.yallabee3.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static android.support.constraint.Constraints.TAG;


public class ProfileFragment extends Fragment {

    Context context;
    @BindView(R.id.profile_image_imageView)
    ImageView profileImageImageView;
    @BindView(R.id.profile_username_textview)
    TextView profileUsernameTextview;
    @BindView(R.id.profile_email_textview)
    TextView profileEmailTextview;
    @BindView(R.id.profile_logout_button)
    Button profileLogoutButton;
    Unbinder unbinder;

    DatabaseReference myRef;
    FirebaseAuth auth;

    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    List<Favourite> favourites = new ArrayList<>();

    ShowAdapter adapter2;
    List<Product> products2 = new ArrayList<>();


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        unbinder = ButterKnife.bind(this, view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(user.getUid());
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

        RecyclerView recyclerView = view.findViewById(R.id.profile_products_recyclerView);
        adapter2 = new ShowAdapter(products2, getContext(), ShowAdapter.LAND_SCAPE_PRODUCT);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter2);


        //

        Query query2 = FirebaseDatabase.getInstance().getReference("Favourite");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Product product = snapshot.getValue(Product.class);
//                    String id = snapshot.getKey();
                    Favourite favourite = snapshot.getValue(Favourite.class);
                    String id = snapshot.getKey();

                    assert favourite != null;
                    favourite.setRootId(id);
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
                    String userId = favourite.getUserId();
                    Log.d(TAG, "onDataChange: " + productId);
                    if (userId.equals(user.getUid())) {
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                        myRef.child("Products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
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

                        RecyclerView recyclerView2 = view.findViewById(R.id.profile_favourites_recyclerView);
                        adapter = new ShowAdapter(products, getContext(), ShowAdapter.LAND_SCAPE_PRODUCT);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView2.setAdapter(adapter);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.profile_logout_button)
    public void onViewClicked() {
        FirebaseAuth.getInstance().signOut();
//      startActivity(new Intent(context, SignInActivity.class)); //Go back to home page
        Intent i = new Intent(getActivity(), SignInActivity.class);
        startActivity(i);
    }

}
