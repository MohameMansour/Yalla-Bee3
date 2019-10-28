package com.example.yallabee3.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ShowSubActivity;
import com.example.yallabee3.adapt_hold.adapter.CategoryAdapter;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Categery;
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
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class CategeryFragment extends Fragment {

    DatabaseReference myRef;
    List<Categery> categeries = new ArrayList<>();
    CategoryAdapter adapter;
    String DeviceLang;

    //    List<Product> Vehicles, Properties, MobilePhones, Electronics, Home,
//            Fashion, Pets, Kids, Sporting, Hobbies, Jobs, Business, Services = new ArrayList<>();
//
    ShowAdapter VehiclesAdapter, PropertiesAdapter, MobilePhonesAdapter, ElectronicsAdapter, HomeAdapter, FashionAdapter, PetsAdapter, KidsAdapter, SportingAdapter, HobbiesAdapter, JobsAdapter, BusinessAdapter, ServicesAdapter;

    List<Product> Vehicles = new ArrayList<>();
    //    ShowAdapter VehiclesAdapter;
    List<Product> Properties = new ArrayList<>();
    //    ShowAdapter PropertiesAdapter;
    List<Product> MobilePhones = new ArrayList<>();
    //    ShowAdapter MobilePhonesAdapter;
    List<Product> Electronics = new ArrayList<>();
    List<Product> Homes = new ArrayList<>();
    List<Product> Fashions = new ArrayList<>();
    List<Product> Pets = new ArrayList<>();
    List<Product> Kids = new ArrayList<>();
    List<Product> Sportings = new ArrayList<>();
    List<Product> Hobbies = new ArrayList<>();
    List<Product> Jobs = new ArrayList<>();
    List<Product> Business = new ArrayList<>();
    List<Product> Services = new ArrayList<>();


    //
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    //
    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    RecyclerView recyclerView;

    String recivedcatid;
    Context context;
    String currentcountryfromfirebase;
    @BindView(R.id.vehicles_layout_text)
    TextView vehiclesLayoutText;

    Unbinder unbinder;
    private ConstraintLayout vehiclesLayout, propertiesLayout, mobilephoneLayout, electronicsLayout, homeLayout, fashionLayout, petsLayout, kidsLayout, sportingLayout, hobbiesLayout, jobsLayout, businessLayout, servicesLayout;


    public CategeryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categery, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.keepSynced(false);

        vehiclesLayout = view.findViewById(R.id.vehicles_layout);
        propertiesLayout = view.findViewById(R.id.properties_layout);
        mobilephoneLayout = view.findViewById(R.id.mobilephones_layout);
        electronicsLayout = view.findViewById(R.id.electronics_layout);
        homeLayout = view.findViewById(R.id.home_layout);
        fashionLayout = view.findViewById(R.id.fashion_layout);
        petsLayout = view.findViewById(R.id.pets_layout);
        kidsLayout = view.findViewById(R.id.kids_layout);
        sportingLayout = view.findViewById(R.id.sporting_layout);
        hobbiesLayout = view.findViewById(R.id.hobbies_layout);
        jobsLayout = view.findViewById(R.id.jobs_layout);
        businessLayout = view.findViewById(R.id.business_layout);
        servicesLayout = view.findViewById(R.id.services_layout);

        auth = FirebaseAuth.getInstance();

        SharedPreferences sp = getActivity().getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");
        Log.d(TAG, "onCreateView: " + currentcountryfromfirebase);

        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();
//        Toast.makeText(context, "hh " + DeviceLang , Toast.LENGTH_SHORT).show();

        CallCategory();
        CallVehicles();
        CallProperties();
        CallMobilePhones();
        CallElectronics();
        CallHome();
        CallFashion();
        CallPets();
        CallKids();
        CallSporting();
        CallHobbies();
        CallJobs();
        CallBusiness();
        CallServices();

        context = getActivity().getApplicationContext();

        recyclerView = view.findViewById(R.id.categ_info_recyclerView);


        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();

//
//        return view;
        //da a7san bs m4 now
//        myRef.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                    //            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                products.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Product product = snapshot.getValue(Product.class);
//
//                    String id = snapshot.getKey();
//
//                    assert product != null;
//                    product.setId(id);
//
//                    String productId = product.getId();
//                    String catId = product.getCatId();
//                    Log.d(TAG, "catid: " + catId);
//
//                    Query query2 = FirebaseDatabase.getInstance().getReference("Products")
//                            .child("-LoKK0xhnYlc15oft-kL").orderByChild("catId").equalTo("Vehicles");
//
//                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            Product product = dataSnapshot.getValue(Product.class);
//                            products.add(product);
//                            showAdapter.notifyDataSetChanged();
////                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    RecyclerView recyclerView2 = view.findViewById(R.id.categ2_info_recyclerView);
//                    showAdapter = new ShowAdapter(products, context);
//                    recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//                    recyclerView2.setAdapter(showAdapter);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });      l7d hna aho

        adapter = new CategoryAdapter(categeries, context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView1 = view.findViewById(R.id.vehicles_info_recyclerView);
        VehiclesAdapter = new ShowAdapter(Vehicles, context);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(VehiclesAdapter);

        RecyclerView recyclerView2 = view.findViewById(R.id.properties_info_recyclerView);
        PropertiesAdapter = new ShowAdapter(Properties, context);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(PropertiesAdapter);

        RecyclerView recyclerView3 = view.findViewById(R.id.mobilephones_info_recyclerView);
        MobilePhonesAdapter = new ShowAdapter(MobilePhones, context);
        recyclerView3.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setAdapter(MobilePhonesAdapter);

        RecyclerView recyclerView4 = view.findViewById(R.id.electronics_info_recyclerView);
        ElectronicsAdapter = new ShowAdapter(Electronics, context);
        recyclerView4.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView4.setAdapter(ElectronicsAdapter);

        RecyclerView recyclerView5 = view.findViewById(R.id.home_info_recyclerView);
        HomeAdapter = new ShowAdapter(Homes, context);
        recyclerView5.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView5.setAdapter(HomeAdapter);

        RecyclerView recyclerView6 = view.findViewById(R.id.fashion_info_recyclerView);
        FashionAdapter = new ShowAdapter(Fashions, context);
        recyclerView6.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView6.setAdapter(FashionAdapter);

        RecyclerView recyclerView7 = view.findViewById(R.id.pets_info_recyclerView);
        PetsAdapter = new ShowAdapter(Pets, context);
        recyclerView7.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView7.setAdapter(PetsAdapter);

        RecyclerView recyclerView8 = view.findViewById(R.id.kids_info_recyclerView);
        KidsAdapter = new ShowAdapter(Kids, context);
        recyclerView8.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView8.setAdapter(KidsAdapter);

        RecyclerView recyclerView9 = view.findViewById(R.id.sporting_info_recyclerView);
        SportingAdapter = new ShowAdapter(Sportings, context);
        recyclerView9.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView9.setAdapter(SportingAdapter);

        RecyclerView recyclerView10 = view.findViewById(R.id.hobbies_info_recyclerView);
        HobbiesAdapter = new ShowAdapter(Hobbies, context);
        recyclerView10.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView10.setAdapter(HobbiesAdapter);

        RecyclerView recyclerView11 = view.findViewById(R.id.jobs_info_recyclerView);
        JobsAdapter = new ShowAdapter(Jobs, context);
        recyclerView11.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView11.setAdapter(JobsAdapter);

        RecyclerView recyclerView12 = view.findViewById(R.id.business_info_recyclerView);
        BusinessAdapter = new ShowAdapter(Business, context);
        recyclerView12.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView12.setAdapter(BusinessAdapter);

        RecyclerView recyclerView13 = view.findViewById(R.id.services_info_recyclerView);
        ServicesAdapter = new ShowAdapter(Services, context);
        recyclerView13.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView13.setAdapter(ServicesAdapter);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    public void CallCategory() {

        if (DeviceLang.equals("ar")) {
            myRef.child("CategoryAr").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    categeries.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Categery categery = snapshot.getValue(Categery.class);
                        categeries.add(categery);
                        adapter.notifyDataSetChanged();
                    }
                    Collections.reverse(categeries);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            myRef.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    categeries.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Categery categery = snapshot.getValue(Categery.class);
                        categeries.add(categery);
                        adapter.notifyDataSetChanged();
                    }
                    Collections.reverse(categeries);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void CallVehicles() {
        Query query1;
        if (currentcountryfromfirebase.equals("مصر")) {
            query1 = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Vehicles").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query1 = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Vehicles").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query1 = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Vehicles").limitToLast(6);

        } else {
            query1 = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Vehicles").limitToLast(6);

        }
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicles.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Vehicles.add(product);
                        VehiclesAdapter.notifyDataSetChanged();
                    }
                    Collections.reverse(Vehicles);
                    vehiclesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallProperties() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Properties").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Properties").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Properties").limitToLast(6);

        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Properties").limitToLast(6);

        }
//        query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Properties").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Properties.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Properties.add(product);
                        PropertiesAdapter.notifyDataSetChanged();
                    }
                    propertiesLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Properties);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CallMobilePhones() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Mobile Phones, Accessories").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Mobile Phones, Accessories").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Mobile Phones, Accessories").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Mobile Phones, Accessories").limitToLast(6);

        }
//        query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Mobile Phones, Accessories").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MobilePhones.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        MobilePhones.add(product);
                        MobilePhonesAdapter.notifyDataSetChanged();
                    }
                    mobilephoneLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(MobilePhones);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallElectronics() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Electronics, Home Appliances").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Electronics, Home Appliances").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Electronics, Home Appliances").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Electronics, Home Appliances").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Electronics, Home Appliances").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Electronics.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Electronics.add(product);
                        ElectronicsAdapter.notifyDataSetChanged();
                    }
                    electronicsLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Electronics);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallHome() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Home, Garden").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Home, Garden").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Home, Garden").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Home, Garden").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Home, Garden").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Homes.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Homes.add(product);
                        HomeAdapter.notifyDataSetChanged();
                    }
                    homeLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Homes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallFashion() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Fashion, Beauty").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Fashion, Beauty").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Fashion, Beauty").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Fashion, Beauty").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Fashion, Beauty").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Fashions.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Fashions.add(product);
                        FashionAdapter.notifyDataSetChanged();
                    }
                    fashionLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Fashions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallPets() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Pets").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Pets").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Pets").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Pets").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Pets").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pets.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Pets.add(product);
                        PetsAdapter.notifyDataSetChanged();
                    }
                    petsLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Pets);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallKids() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Kids, Babies").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Kids, Babies").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Kids, Babies").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Kids, Babies").limitToLast(6);
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Kids, Babies").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Kids.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Kids.add(product);
                        KidsAdapter.notifyDataSetChanged();
                    }
                    kidsLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Kids);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallSporting() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Sporting Goods, Bikes").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Sporting Goods, Bikes").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Sporting Goods, Bikes").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Sporting Goods, Bikes").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Sporting Goods, Bikes").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sportings.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Sportings.add(product);
                        SportingAdapter.notifyDataSetChanged();
                    }
                    sportingLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Sportings);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallHobbies() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Hobbies, Music, Art, Books").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Hobbies, Music, Art, Books").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Hobbies, Music, Art, Books").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Hobbies, Music, Art, Books").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Hobbies, Music, Art, Books").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Hobbies.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Hobbies.add(product);
                        HobbiesAdapter.notifyDataSetChanged();
                    }
                    hobbiesLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Hobbies);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallJobs() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Jobs").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Jobs").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Jobs").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Jobs").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Jobs").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Jobs.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Jobs.add(product);
                        JobsAdapter.notifyDataSetChanged();
                    }
                    jobsLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Jobs);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallBusiness() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Business, Industrial").limitToLast(6);

        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Business, Industrial").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Business, Industrial").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Business, Industrial").limitToLast(6);

        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Business, Industrial").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Business.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Business.add(product);
                        BusinessAdapter.notifyDataSetChanged();
                    }
                    businessLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Business);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void CallServices() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("catId").equalTo("Services").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("catId").equalTo("Services").limitToLast(6);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("catId").equalTo("Services").limitToLast(6);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Services").limitToLast(6);
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Services").limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Services.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Services.add(product);
                        ServicesAdapter.notifyDataSetChanged();
                    }
                    servicesLayout.setVisibility(View.VISIBLE);
                    Collections.reverse(Services);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @OnClick({R.id.vehicles_layout, R.id.properties_layout, R.id.mobilephones_layout, R.id.electronics_layout, R.id.home_layout, R.id.fashion_layout, R.id.pets_layout, R.id.kids_layout, R.id.sporting_layout, R.id.hobbies_layout, R.id.jobs_layout, R.id.business_layout, R.id.services_layout})
    public void onViewClicked(View view) {
        Intent intent;
        intent = new Intent(getContext(), ShowSubActivity.class);
        String name;
        switch (view.getId()) {
            case R.id.vehicles_layout:
                name = "Vehicles";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.properties_layout:
                name = "Properties";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.mobilephones_layout:
                name = "Mobile Phones, Accessories";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.electronics_layout:
                name = "Electronics, Home Appliances";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.home_layout:
                name = "Home, Garden";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.fashion_layout:
                name = "Fashion, Beauty";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.pets_layout:
                name = "Pets";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.kids_layout:
                name = "Kids, Babies";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.sporting_layout:
                name = "Sporting Goods, Bikes";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.hobbies_layout:
                name = "Hobbies, Music, Art, Books";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.jobs_layout:
                name = "Jobs";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.business_layout:
                name = "Business, Industrial";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
            case R.id.services_layout:
                name = "Services";
                intent.putExtra("intent_object", name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                break;
        }
    }
}