package com.example.yallabee3.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.fragment.AddEgyFragment;
import com.example.yallabee3.fragment.AddEmFragment;
import com.example.yallabee3.fragment.AddFragment;
import com.example.yallabee3.fragment.AddSodFragment;
import com.example.yallabee3.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.constraint.Constraints.TAG;


public class ShowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();
    List<Product> productss = new ArrayList<>();
    List<Product> productsss = new ArrayList<>();
    List<Product> lowestproducts = new ArrayList<>();
    List<Product> highestproducts = new ArrayList<>();


    ShowAdapter defultadapter;
    ShowAdapter adapterr;
    ShowAdapter lowestadapterr;
    ShowAdapter higestadapterr;


    RecyclerView recyclerView;
    RecyclerView recyclerViewSearch;
    RecyclerView lowestrecyclerView;
    RecyclerView highestrecyclerView;


    String compid;
    FirebaseAuth auth;
    FirebaseUser user;
    @BindView(R.id.add_btn)
    Button addBtn;
    String cate;
    FrameLayout frameLayout;
    DatabaseReference ref;
    Query querys;

    SearchView searchView;
    AppCompatSpinner spinner;
    @BindView(R.id.back_productt)
    ImageView backProductt;

    String rate;
    @BindView(R.id.moree_spinner)
    AppCompatSpinner moreSpinner;

    private String recivedName;
    private String recivedIdForProduct;

    Toolbar toolbar;

    ArrayList<Integer> integerList;
    ArrayList<String> integerList2;
    ArrayList<ArrayList<Integer>> mother;
    String currentcountryfromfirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);


        SharedPreferences sp = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");

        Log.d(TAG, "onBindViewHolder: " + currentcountryfromfirebase);

        if (currentcountryfromfirebase.equals("مصر")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsEgy");
            querys = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo(cate);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsSod");
            querys = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo(cate);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ProductsEm");
            querys = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo(cate);
        } else {
            ref = FirebaseDatabase.getInstance().getReference().child("Products");
            querys = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo(cate);
        }

        integerList = new ArrayList<Integer>();
        integerList2 = new ArrayList<String>();
        mother = new ArrayList<ArrayList<Integer>>();

        //
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("Products")
//                .orderBy("price")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//                        for(DocumentSnapshot snapshot:snapshotList){
//                            Log.d(TAG, "onSuccess: " + snapshot.getData());
//                        }
//                    }
//                });
        //
        toolbar = findViewById(R.id.my_toolbar);
        myRef = FirebaseDatabase.getInstance().getReference();
        searchView = findViewById(R.id.searchview);
        frameLayout = findViewById(R.id.frameLayout);
        recyclerViewSearch = findViewById(R.id.recyclerview_search);
        spinner = findViewById(R.id.spinner);
        lowestrecyclerView = findViewById(R.id.lowest_price_recyclerView);
        highestrecyclerView = findViewById(R.id.highest_price_recyclerView);

        recivedName = getIntent().getExtras().get("intent_object").toString();
//        Toast.makeText(this, recivedName, Toast.LENGTH_SHORT).show();

        recivedIdForProduct = getIntent().getExtras().get("intent_object").toString();
//        Toast.makeText(this, recivedIdForProduct, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        compid = user.getUid();


//  true      Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo(recivedName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.categories
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select an Category");

        //
        ArrayAdapter<CharSequence> aadapter = ArrayAdapter
                .createFromResource(this, R.array.rate
                        , android.R.layout.simple_spinner_item);
        aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moreSpinner.setAdapter(aadapter);
        moreSpinner.setOnItemSelectedListener(this);
        moreSpinner.setPrompt("Select Arrange");

        moreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rate = parent.getItemAtPosition(position).toString();
                if (position == 0) {

                    highestrecyclerView.setVisibility(View.GONE);
                    lowestrecyclerView.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
                    if (products.isEmpty()) {
                        Query query;
                        if (currentcountryfromfirebase.equals("مصر")) {
                            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("subCatId").equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("subCatId").equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("الامارات")) {
                            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("subCatId").equalTo(recivedName);
                        } else {
                            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId").equalTo(recivedName);
                        }
//                        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId")
//                                .equalTo(recivedName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                products.clear();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Product product = snapshot.getValue(Product.class);
                                    products.add(product);
                                    defultadapter.notifyDataSetChanged();
                                    Log.d(TAG, "onDataChange: " + product.getId());

                                    String price = product.getPrice();
                                    Integer x = Integer.parseInt(price);
                                    int z = x;
                                    integerList.add(z);

                                    String productId = product.getId();
                                    integerList2.add(productId);

                                }

                                int c;
                                for (c = 0; c < integerList2.size(); c++) {
                                    Log.d(TAG, "newday222: " + integerList2.get(c));
                                }
//                                Collections.sort(integerList);
                                int i;
                                for (i = 0; i < integerList.size(); i++) {
                                    Log.d(TAG, "newday2: " + integerList.get(i));
                                }

                                Collections.reverse(products);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        recyclerView = findViewById(R.id.driver_info_recyclerView);
                        defultadapter = new ShowAdapter(products, ShowActivity.this, ShowAdapter.LAND_SCAPE_PRODUCT);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                        recyclerView.setAdapter(defultadapter);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }

                if (position == 1) {
                    //
//                    recyclerView.setVisibility(View.GONE);
//                    highestrecyclerView.setVisibility(View.GONE);
//                    lowestrecyclerView.setVisibility(View.VISIBLE);
//                    Toast.makeText(ShowActivity.this, rate, Toast.LENGTH_SHORT).show();
//                    if (lowestrecyclerView.getVisibility() == View.VISIBLE && lowestproducts.isEmpty()) {
//                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
//                        myRef.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                products.clear();
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    Product categery = snapshot.getValue(Product.class);
//                                    Log.d(TAG, "Value1 is: " + categery.getId());
//                                    Log.d(TAG, "Value5 is: " + categery.getSubCatId());
//                                    Collections.sort(integerList);
//                                    int i;
//                                    for (i = 0; i < integerList.size(); i++) {
//                                        Log.d(TAG, "newday2: " + integerList.get(i));
//                                        String newprice = integerList.get(i).toString();
//                                        Log.d(TAG, "newday3: " + newprice);
//
//                                        if (categery.getSubCatId().equals(recivedName) &&
//                                                categery.getPrice().equals(newprice)) {
//                                            String ff = categery.getId();
//                                            Log.d(TAG, "onDataChange: " + ff);
//                                            Log.d(TAG, "onData: " + categery.getSubCatId());
//                                            Log.d(TAG, "onData55: " + categery.getPrice());
//                                            String vv = categery.getPrice();
//                                            Query querysss = FirebaseDatabase.getInstance().getReference("Products").child(ff)
//                                                    .orderByChild("price").equalTo(vv);
//                                            querysss.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Product product = dataSnapshot.getValue(Product.class);
//                                                    lowestproducts.add(product);
//                                                    lowestadapterr.notifyDataSetChanged();
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//
//                                            });
//                                        }
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                            }
//                        });
//                        lowestadapterr = new ShowAdapter(lowestproducts, ShowActivity.this, ShowAdapter.LAND_SCAPE_PRODUCT);
//                        lowestrecyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
//                        lowestrecyclerView.setAdapter(lowestadapterr);
//                    }
                    //

                    recyclerView.setVisibility(View.GONE);
                    highestrecyclerView.setVisibility(View.GONE);
                    lowestrecyclerView.setVisibility(View.VISIBLE);
//                    Toast.makeText(ShowActivity.this, rate, Toast.LENGTH_SHORT).show();
                    if (lowestrecyclerView.getVisibility() == View.VISIBLE && lowestproducts.isEmpty()) {
                        Query queryss;
                        if (currentcountryfromfirebase.equals("مصر")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("الامارات")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else {
                            queryss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        }
//                        Query queryss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId")
//                                .equalTo(recivedName);
                        queryss.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Collections.sort(integerList);
                                int i;
                                for (i = 0; i < integerList.size(); i++) {
                                    Log.d(TAG, "newday2: " + integerList.get(i));
                                    String newprice = integerList.get(i).toString();
                                    Log.d(TAG, "newday3: " + newprice);

                                    Query querysss;
                                    if (currentcountryfromfirebase.equals("مصر")) {
                                        querysss = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("price")
                                                .equalTo(newprice);
                                    } else {
                                        querysss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("price")
                                                .equalTo(newprice);
                                    }
//                                    Query querysss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("price")
//                                            .equalTo(newprice);
                                    querysss.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Product product = dataSnapshot.getChildren().iterator().next()
                                                    .getValue(Product.class);
                                            lowestproducts.add(product);
                                            lowestadapterr.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    lowestadapterr = new ShowAdapter(lowestproducts, ShowActivity.this, ShowAdapter.LAND_SCAPE_PRODUCT);
                                    lowestrecyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                                    lowestrecyclerView.setAdapter(lowestadapterr);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
//                }
                        });
                    }
                }
                if (position == 2) {
                    recyclerView.setVisibility(View.GONE);
                    lowestrecyclerView.setVisibility(View.GONE);
                    highestrecyclerView.setVisibility(View.VISIBLE);

//                    Toast.makeText(ShowActivity.this, rate, Toast.LENGTH_SHORT).show();


                    if (highestrecyclerView.getVisibility() == View.VISIBLE && highestproducts.isEmpty()) {
//
//                        Collections.sort(integerList);
//                        int i;
//                        for (i = 0; i < integerList.size(); i++) {
//                            Log.d(TAG, "newday2: " + integerList.get(i));
//                            String newprice = integerList.get(i).toString();
//                            Log.d(TAG, "newday3: " + newprice);
                        Query queryss;
                        if (currentcountryfromfirebase.equals("مصر")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else if (currentcountryfromfirebase.equals("الامارات")) {
                            queryss = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        } else {
                            queryss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId")
                                    .equalTo(recivedName);
                        }
//                        Query queryss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId")
//                                .equalTo(recivedName);
                        queryss.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                productsss.clear();
                                Collections.reverse(integerList);
                                int i;
                                for (i = 0; i < integerList.size(); i++) {
                                    Log.d(TAG, "newday2: " + integerList.get(i));
                                    String newprice = integerList.get(i).toString();
                                    Log.d(TAG, "newday3: " + newprice);

                                    Query querysss;
                                    if (currentcountryfromfirebase.equals("مصر")) {
                                        querysss = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("price")
                                                .equalTo(newprice);
                                    } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                                        querysss = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("price")
                                                .equalTo(newprice);
                                    } else if (currentcountryfromfirebase.equals("الامارات")) {
                                        querysss = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("price")
                                                .equalTo(newprice);
                                    } else {
                                        querysss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("price")
                                                .equalTo(newprice);
                                    }
//                                    Query querysss = FirebaseDatabase.getInstance().getReference("Products").orderByChild("price")
//                                            .equalTo(newprice);
                                    querysss.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Product product = dataSnapshot.getChildren().iterator().next()
                                                    .getValue(Product.class);
                                            highestproducts.add(product);
                                            higestadapterr.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    higestadapterr = new ShowAdapter(highestproducts, ShowActivity.this, ShowAdapter.LAND_SCAPE_PRODUCT);
                                    highestrecyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                                    highestrecyclerView.setAdapter(higestadapterr);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
//                }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //
    }

//    public void ss(int c) {
//        integerList2 = new ArrayList<Integer>();
//        integerList2.add(c);
//
//        for(int i=0; i < integerList2.size(); i++){
//            Log.d(TAG, "onCreateView1: " + integerList2.get(i));
//        }
//
//        Collections.sort(integerList2);
//        for (int i = 0; i < integerList2.size(); i++) {
//            Log.d(TAG, "onCreateV22: " + integerList2.get(i));
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (ref != null) {
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            Product product = ds.getValue(Product.class);
//                            productss.add(product);
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(ShowActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
        if (searchView != null) {

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinner.setVisibility(View.VISIBLE);
                    moreSpinner.setVisibility(View.VISIBLE);
                }
            });

            searchView.setQueryHint("Search");
            View searchFrame = searchView.findViewById(R.id.search_edit_frame);
            searchFrame.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_message, getTheme()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                    search(s);
                    return false;
                }
            });
        }

    }

    private void search(String str) {
        List<Product> myproducts = new ArrayList<>();
        for (Product object : productss) {
            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                myproducts.add(object);
            }
            ShowAdapter myadapter = new ShowAdapter(myproducts, ShowActivity.this,
                    ShowAdapter.LAND_SCAPE_PRODUCT);
            recyclerViewSearch.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
            recyclerViewSearch.setAdapter(myadapter);
        }

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerViewSearch.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                moreSpinner.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }


    @OnClick(R.id.add_btn)
    public void onViewClicked() {
//        Intent i = new Intent(ShowActivity.this , AddFragment.class);
//        startActivity(i);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentcountryfromfirebase.equals("مصر")) {
            fragmentTransaction.replace(R.id.content_frame, new AddEgyFragment()).addToBackStack(null).commit();
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            fragmentTransaction.replace(R.id.content_frame, new AddSodFragment()).addToBackStack(null).commit();
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            fragmentTransaction.replace(R.id.content_frame, new AddEmFragment()).addToBackStack(null).commit();
        } else {
            fragmentTransaction.replace(R.id.content_frame, new AddFragment()).addToBackStack(null).commit();
        }
//        fragmentTransaction.replace(R.id.content_frame, new AddFragment()).addToBackStack(null).commit();
        recyclerView.setVisibility(View.GONE);
        addBtn.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        moreSpinner.setVisibility(View.GONE);
        lowestrecyclerView.setVisibility(View.GONE);
        highestrecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recyclerView.setVisibility(View.VISIBLE);
        addBtn.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        moreSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cate = parent.getItemAtPosition(position).toString();
//        Toast.makeText(this, cate, Toast.LENGTH_SHORT).show();
        if (cate.equals("عربيات وقطع غيار")) {
            cate = "Vehicles";
        } else if (cate.equals("عقارات")) {
            cate = "Properties";
        } else if (cate.equals("موبايلات واكسسواراتها")) {
            cate = "Mobile Phones, Accessories";
        } else if (cate.equals("الكترونيات وأجهزة منزلية")) {
            cate = "Electronics, Home Appliances";
        } else if (cate.equals("المنزل والحديقة")) {
            cate = "Home, Garden";
        } else if (cate.equals("الموضة والجمال")) {
            cate = "Fashion, Beauty";
        } else if (cate.equals("حيوانات أليفة")) {
            cate = "Pets";
        } else if (cate.equals("مستلزمات أطفال")) {
            cate = "Kids, Babies";
        } else if (cate.equals("دراجات ومعدات رياضية")) {
            cate = "Sporting Goods, Bikes";
        } else if (cate.equals("موسيقى وفنون وكتب")) {
            cate = "Hobbies, Music, Art, Books";
        } else if (cate.equals("وظائف")) {
            cate = "Jobs";
        } else if (cate.equals("تجارة وصناعة")) {
            cate = "Business, Industrial";
        } else if (cate.equals("خدمات")) {
            cate = "Services";
        }

        recyclerViewSearch.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo(cate);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo(cate);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo(cate);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo(cate);
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo(cate);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    productss.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Product product = ds.getValue(Product.class);
                        productss.add(product);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.back_productt)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_productt:
                finish();
                break;
        }
    }
}
