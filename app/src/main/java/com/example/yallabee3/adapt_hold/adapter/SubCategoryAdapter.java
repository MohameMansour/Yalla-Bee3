package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ShowActivity;
import com.example.yallabee3.adapt_hold.holder.SubCategoryHolder;
import com.example.yallabee3.model.SubCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder> {

    private List<SubCategory> subCategories;
    private Context context;
    String currentcountryfromfirebase;

    private Context getContext() {
        return context;
    }

    public SubCategoryAdapter(List<SubCategory> subCategories, Context context) {
        this.subCategories = subCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        return new SubCategoryHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        final SubCategory subCategory = subCategories.get(position);

        holder.subCategoyNameTextView.setText(subCategory.getName());
//        holder.subCategoyNumberTextView.setText(subCategory.getNumberOfProducts());

        String subCatId = subCategory.getId();

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
        SharedPreferences sp = getContext().getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");
        Log.d(TAG, "onBindViewHolder: " + currentcountryfromfirebase);
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("subCatId").equalTo(subCatId);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("subCatId").equalTo(subCatId);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("subCatId").equalTo(subCatId);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId").equalTo(subCatId);
        }
//        query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId").equalTo(subCatId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.subCategoyNumberTextView.setText(dataSnapshot.getChildrenCount() + " Item");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ShowActivity.class);
            String subCatIds = subCategory.getName();

//            String subCatId = subCategory.getId();

            if (subCatIds.equals("قوارب")) {
                subCatIds = "Boats";
            } else if (subCatIds.equals("اكسسوارات عربيات")) {
                subCatIds = "Car Accessories";
            } else if (subCatIds.equals("عربيات")) {
                subCatIds = "Cars";
            } else if (subCatIds.equals("موتوسيكلات")) {
                subCatIds = "Motorcycles";
            } else if (subCatIds.equals("وسائل نقل أخرى")) {
                subCatIds = "Other Vehicles";
            } else if (subCatIds.equals("يخوت")) {
                subCatIds = "Spare parts";
            } else if (subCatIds.equals("اتوبيسات وعربيات نقل")) {
                subCatIds = "Trucks, buses";
            } else if (subCatIds.equals("عقارات للإيجار")) {
                subCatIds = "Properties for Rent";
            } else if (subCatIds.equals("عقارات للبيع")) {
                subCatIds = "Properties for Sale";
            } else if (subCatIds.equals("موبايلات")) {
                subCatIds = "Mobile Phones";
            } else if (subCatIds.equals("إكسسوارات موبايلات")) {
                subCatIds = "Mobile Accessories";
            } else if (subCatIds.equals("تلفزيونات وصوتيات")) {
                subCatIds = "TV - Audio - Video";
            } else if (subCatIds.equals("كمبيوتر وتابلت")) {
                subCatIds = "Computers - Tablets";
            } else if (subCatIds.equals("أجهزة وألعاب فيديو")) {
                subCatIds = "Video games - Consoles";
            } else if (subCatIds.equals("كاميرات وتصوير")) {
                subCatIds = "Cameras - Imaging";
            } else if (subCatIds.equals("أجهزة منزلية")) {
                subCatIds = "Home Appliances";
            } else if (subCatIds.equals("اكسسوارات وزينة")) {
                subCatIds = "Decoration - Accessories";
            } else if (subCatIds.equals("أثاث")) {
                subCatIds = "Furniture";
            } else if (subCatIds.equals("الحدائق والمناطق الخارجية")) {
                subCatIds = "Garden - Outdoor";
            } else if (subCatIds.equals("معدات وأدوات طبخ")) {
                subCatIds = "Kitchenware";
            } else if (subCatIds.equals("منزل وحديقة أخرى")) {
                subCatIds = "Other Home - Garden";
            } else if (subCatIds.equals("ملابس واكسسوارات")) {
                subCatIds = "Clothing - Accessories";
            } else if (subCatIds.equals("ساعات ومجوهرات")) {
                subCatIds = "Jewelry - Watches";
            } else if (subCatIds.equals("صحة وجمال")) {
                subCatIds = "Health - Beauty - Cosmetics";
            } else if (subCatIds.equals("قطط")) {
                subCatIds = "Cats";
            } else if (subCatIds.equals("كلاب")) {
                subCatIds = "Dogs";
            } else if (subCatIds.equals("خيل")) {
                subCatIds = "Horses";
            } else if (subCatIds.equals("اسماك")) {
                subCatIds = "Fishes";
            } else if (subCatIds.equals("طيور")) {
                subCatIds = "Birds";
            } else if (subCatIds.equals("حيوانات اليفه اخرى")) {
                subCatIds = "Other Pets";
            } else if (subCatIds.equals("ملابس الاطفال والرضع")) {
                subCatIds = "Clothes for Kids and Babies";
            } else if (subCatIds.equals("لعب اطفال")) {
                subCatIds = "Toys";
            } else if (subCatIds.equals("سراير وعربات أطفال")) {
                subCatIds = "Cribs - Strollers";
            } else if (subCatIds.equals("اكسسوارات")) {
                subCatIds = "Accessories";
            } else if (subCatIds.equals("مستلزمات أطفال أخرى")) {
                subCatIds = "Other Kids - Babies";
            } else if (subCatIds.equals("معدات رياضية")) {
                subCatIds = "Sporting Goods";
            } else if (subCatIds.equals("دراجات")) {
                subCatIds = "Bikes";
            } else if (subCatIds.equals("معدات رياضية خارجية")) {
                subCatIds = "Outdoor Equipment";
            } else if (subCatIds.equals("تحف ومقتنيات")) {
                subCatIds = "Antiques - Collectibles";
            } else if (subCatIds.equals("كتب")) {
                subCatIds = "Books";
            } else if (subCatIds.equals("أفلام وموسيقى")) {
                subCatIds = "Movies - Music";
            } else if (subCatIds.equals("آلات موسيقية")) {
                subCatIds = "Musical instruments";
            } else if (subCatIds.equals("اغراض اخرى")) {
                subCatIds = "Other items";
            } else if (subCatIds.equals("ادوات دراسية")) {
                subCatIds = "Study tools";
            } else if (subCatIds.equals("تذاكر وقسائم")) {
                subCatIds = "Tickets - Vouchers";
            } else if (subCatIds.equals("محاسبه")) {
                subCatIds = "Accounting";
            } else if (subCatIds.equals("عمارة - هندسة")) {
                subCatIds = "Architecture - Engineering";
            } else if (subCatIds.equals("فنون - تصميم")) {
                subCatIds = "Art - Design";
            } else if (subCatIds.equals("تطوير الاعمال")) {
                subCatIds = "Business Development";
            } else if (subCatIds.equals("إنشاءات")) {
                subCatIds = "Construction";
            } else if (subCatIds.equals("اعمال استشاريه")) {
                subCatIds = "Consulting";
            } else if (subCatIds.equals("تعليم")) {
                subCatIds = "Education";
            } else if (subCatIds.equals("اعمال اداريه")) {
                subCatIds = "Executive";
            } else if (subCatIds.equals("توظيف - موارد بشرية")) {
                subCatIds = "HR - Recruiting";
            } else if (subCatIds.equals("سياحة وفنادق وضيافة")) {
                subCatIds = "Hospitality";
            } else if (subCatIds.equals("تكنولوجيا المعلومات - إتصالات")) {
                subCatIds = "IT - Telecom";
            } else if (subCatIds.equals("يبحث عن عمل")) {
                subCatIds = "Jobs Wanted";
            } else if (subCatIds.equals("تسويق - علاقات عامة")) {
                subCatIds = "Marketing - PR";
            } else if (subCatIds.equals("طب - صحه")) {
                subCatIds = "Medical - Health";
            } else if (subCatIds.equals("اخرى")) {
                subCatIds = "Other";
            } else if (subCatIds.equals("تجزئه")) {
                subCatIds = "Retail";
            } else if (subCatIds.equals("مبيعات")) {
                subCatIds = "Sales";
            } else if (subCatIds.equals("سكرتاريه")) {
                subCatIds = "Secretarial";
            } else if (subCatIds.equals("معدات مصانع")) {
                subCatIds = "Factories Equipment";
            } else if (subCatIds.equals("معدات طبيه")) {
                subCatIds = "Medical Equipment";
            } else if (subCatIds.equals("معدات ثقيله")) {
                subCatIds = "Heavy Equipment";
            } else if (subCatIds.equals("مستلزمات مطاعم")) {
                subCatIds = "Restaurants Equipment";
            } else if (subCatIds.equals("تصفيه محلات")) {
                subCatIds = "Shops Liquidation";
            } else if (subCatIds.equals("تجاره و صناعه اخرى")) {
                subCatIds = "Other Business - Industrial";
            } else if (subCatIds.equals("خدمات شركات")) {
                subCatIds = "Business Services";
            } else if (subCatIds.equals("تصليح سيارات")) {
                subCatIds = "Car Repairs";
            } else if (subCatIds.equals("خدمات منزليه")) {
                subCatIds = "Domestic Services";
            } else if (subCatIds.equals("حفلات")) {
                subCatIds = "Events";
            } else if (subCatIds.equals("خدمات محليه")) {
                subCatIds = "Home";
            } else if (subCatIds.equals("توصيل و شحن")) {
                subCatIds = "Movers";
            } else if (subCatIds.equals("خدمات اخرى")) {
                subCatIds = "Other Services";
            } else if (subCatIds.equals("خدمات شخصيه")) {
                subCatIds = "Personal Services";
            } else if (subCatIds.equals("حيوانات اليفه")) {
                subCatIds = "Pets";
            } else if (subCatIds.equals("دروس خصوصيه")) {
                subCatIds = "Private Tutors";
            }

            intent.putExtra("intent_object", subCatIds);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }
}
