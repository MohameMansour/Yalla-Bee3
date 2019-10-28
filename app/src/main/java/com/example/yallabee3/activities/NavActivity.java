package com.example.yallabee3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.fragment.AddEgyFragment;
import com.example.yallabee3.fragment.AddEmFragment;
import com.example.yallabee3.fragment.AddFragment;
import com.example.yallabee3.fragment.AddSodFragment;
import com.example.yallabee3.fragment.CategoriesFragment;
import com.example.yallabee3.fragment.ChatFragment;
import com.example.yallabee3.fragment.HomeFragment;
import com.example.yallabee3.fragment.ProfileFragment;
import com.example.yallabee3.model.Product;
import com.example.yallabee3.model.User;
import com.example.yallabee3.notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class NavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {
    private long backPressedTime;
    private Toast backToast;

    BottomNavigationView bottomNavigationView;
    DatabaseReference ref;
    DatabaseReference myref;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    FrameLayout frameLayout;
    Toolbar toolbar;
    String countryname;
    //    ImageView search;
    FirebaseAuth firebaseAuth;
    String mUID;
    String currentcountryfromfirebase;
    String allcountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        initBottomNavigation();

//        Intent i = this.getIntent();
//        allcountry = i.getExtras().getString("countryId_key");
//        Toast.makeText(this, "" + allcountry, Toast.LENGTH_SHORT).show();

//        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("Current_Country", text);
//        String x = sp.getString("Current_Country" , "hh");
//        editor.apply();
//        Toast.makeText(this, "" +  x , Toast.LENGTH_SHORT).show();

        myref = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerview_search);
        searchView = findViewById(R.id.searchview);
        frameLayout = findViewById(R.id.frameLayout);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);

//        FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();
        myref.child("User").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                countryname = user.getCountry();
                Log.d(TAG, "onDataChange: " + countryname);
//                Toast.makeText(NavActivity.this, ""+countryname, Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("countryfromdatabase", countryname);
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SharedPreferences sp2 = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp2.getString("countryfromdatabase", "new");

        if (currentcountryfromfirebase.equals("مصر")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsEgy");
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsSod");
        } else if (currentcountryfromfirebase.equals("الامارات")){
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsEm");
        } else {
            ref = FirebaseDatabase.getInstance().getReference().child("Products");
        }
//        ref = FirebaseDatabase.getInstance().getReference().child("Products");

//        search = findViewById(R.id.tv_header_title);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NavActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
        firebaseAuth = FirebaseAuth.getInstance();

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");


        loadFragment(new HomeFragment());

        checkUserStatus();
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Press back again to exist", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    private void updateToken(String token) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            mUID = user.getUid();

            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();

        } else {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Product product = ds.getValue(Product.class);
                            products.add(product);
////        adapter.notifyDataSetChanged();
//          adapter = new ShowAdapter(products, NavActivity.this, ShowAdapter.LAND_SCAPE_PRODUCT);
////        recyclerView.setVisibility(View.VISIBLE);
//          recyclerView.setLayoutManager(new LinearLayoutManager(NavActivity.this));
//          recyclerView.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(NavActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (searchView != null) {

            searchView.setQueryHint("Search Here");
//            View searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//            searchTextView.setBackground(
//                    ResourcesCompat.getDrawable(getResources(), R.drawable.back_border_gray, getTheme()));

            View searchFrame = searchView.findViewById(R.id.search_edit_frame);
            searchFrame.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_message, getTheme()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    frameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    bottomNavigationView.setVisibility(View.GONE);
                    search(s);
                    return false;
                }
            });
        }

    }

    private void search(String str) {
        List<Product> myproducts = new ArrayList<>();
        for (Product object : products) {
            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                myproducts.add(object);
            }
            ShowAdapter myadapter = new ShowAdapter(myproducts, NavActivity.this,
                    ShowAdapter.LAND_SCAPE_PRODUCT);
//            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(NavActivity.this));
            recyclerView.setAdapter(myadapter);
        }

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }


    //    private void loadStartFragment() {
//        loadFragment(new MainFragment());
//    }
//
//
//    public void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.frameLayout, fragment);
//        transaction.commit();
//    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void initBottomNavigation() {
//        UIEngine.initialize(NavActivity.this);
        bottomNavigationView = findViewById(R.id.navigation);
        Menu m = bottomNavigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
//                    UIEngine.applyFontToMenuItem(subMenuItem, UIEngine.Fonts.APP_FONT_LIGHT);
                }
            }

//            UIEngine.applyFontToMenuItem(mi, UIEngine.Fonts.APP_FONT_LIGHT);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_category:
                fragment = new CategoriesFragment();
                break;
            case R.id.navigation_add:
                if (countryname.equals("مصر")) {
                    fragment = new AddEgyFragment();
                } else if (countryname.equals("السعودية العربية")) {
                    fragment = new AddSodFragment();
                } else if (countryname.equals("الامارات")){
                    fragment = new AddEmFragment();
                } else {
                    fragment = new AddFragment();
                }
//                fragment = new AddFragment();

                break;
            case R.id.navigation_chat:
                fragment = new ChatFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
//
//        switch (item.getItemId()) {
//            case R.id.navigation_home:
//                loadStartFragment();
//                return true;
//
//            case R.id.navigation_add:
//                loadFragment(new AddFragment());
//                return true;
//            case R.id.navigation_profile:
//                loadFragment(new ProfileFragment());
//                return true;
//            default:
//                break;
//        }
//        return false;
    }

}
