package com.example.yallabee3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.model.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.button2)
    Button send_report_button;
    @BindView(R.id.message_report_editText)
    EditText messageReportEditText;

    String RadioMessage, productId, descriptionMessage , userId;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        Intent i = this.getIntent();
        productId = i.getExtras().getString("productId_key");


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_wrongprice:
                if (checked)
                    RadioMessage = "Wrong Price / Picture / Category";
                break;
            case R.id.radio_inappropriatead:
                if (checked)
                    RadioMessage = "Inappropriate ad content";
                break;
            case R.id.radio_itemsold:
                if (checked)
                    RadioMessage = "Item sold";
                break;
            case R.id.radio_fraudorscam:
                if (checked)
                    RadioMessage = "Fraud or Scam";
                break;
            case R.id.radio_indecentseller:
                if (checked)
                    RadioMessage = "Indecent Seller";
                break;
            case R.id.radio_other:
                if (checked)
                    RadioMessage = "Other";
                break;
        }
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {
        descriptionMessage = messageReportEditText.getText().toString();
        if(TextUtils.isEmpty(descriptionMessage) || TextUtils.isEmpty(RadioMessage)){
            Toast.makeText(this, "Select Reason & Description", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
            DatabaseReference db_ref_Main = fb_db_instance.getReference();
            DatabaseReference blankRecordReference = db_ref_Main;
            DatabaseReference db_ref = blankRecordReference.push();
            String Id = db_ref.getKey();

        Report report = new Report(productId,userId,descriptionMessage,RadioMessage);
        databaseReference.child("Reports").child(productId).child(Id).setValue(report);
        // Missing code
        Toast.makeText(ReportActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
        Intent x = new Intent(ReportActivity.this, NavActivity.class);
        startActivity(x);
        finish();
        }
    }
}
