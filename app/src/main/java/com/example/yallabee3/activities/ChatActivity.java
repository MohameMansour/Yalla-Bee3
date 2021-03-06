package com.example.yallabee3.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.MessageAdapter;
import com.example.yallabee3.model.Chats;
import com.example.yallabee3.model.Message;
import com.example.yallabee3.model.User;
import com.example.yallabee3.notifications.APIService;
import com.example.yallabee3.notifications.Client;
import com.example.yallabee3.notifications.Data;
import com.example.yallabee3.notifications.Response;
import com.example.yallabee3.notifications.Sender;
import com.example.yallabee3.notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;


public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.image_user)
    CircleImageView imageUser;
    @BindView(R.id.username_chat_textview)
    TextView usernameChatTextview;
    @BindView(R.id.online_chat_textview)
    TextView onlineChatTextview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerview;
    @BindView(R.id.message_edittext)
    EditText messageEdittext;
    @BindView(R.id.send_button)
    ImageButton sendButton;
    @BindView(R.id.layout_chat)
    LinearLayout layoutChat;

    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;
    List<Message> messages = new ArrayList<>();
    MessageAdapter adapter;

    String image;
    String anotheruserIdInChat, productId ;
    String getnametoprofile ;
    String reciverproduct;
    String myId;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;
    APIService apiService;
    boolean notify = false;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    DatabaseReference myRef;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        myRef = FirebaseDatabase.getInstance().getReference();
//        FirebaseUser user = firebaseAuth.getCurrentUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerview.setHasFixedSize(true);
        chatRecyclerview.setLayoutManager(linearLayoutManager);

        //create api searvice
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDbRef = firebaseDatabase.getReference("User");

        Intent i = this.getIntent();
        anotheruserIdInChat = i.getExtras().getString("userId_key");
//        anotheruserIdInChat2 = i.getExtras().getString("userId_key");

        getnametoprofile = i.getExtras().getString("anotheruserId_key");
//        getnametoprofile2 = i.getExtras().getString("anotheruserId_key");
        Log.d(TAG, "onCreate: " + anotheruserIdInChat);
        Log.d(TAG, "onCreate1: " + getnametoprofile);
        productId = i.getExtras().getString("productId_key");
        reciverproduct = i.getExtras().getString("reciverId_key");
//        Toast.makeText(this, anotheruserIdInChat, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String myIds = user.getUid();
        if (getnametoprofile.equals(myIds)) {
            getnametoprofile = reciverproduct;
        } else {

        }
        Query userquery = usersDbRef.orderByChild("userId").equalTo(getnametoprofile);
        userquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = "" + ds.child("fullName").getValue();
                    image = "" + ds.child("imgUrl").getValue();

                    if (image != null) {
                        Picasso.get()
                                .load(image)
                                .into(imageUser);
                    } else {
                    }
                    usernameChatTextview.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        readMessage();
        seenMessage();
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

//    @Override
//    public void onBackPressed() {
//
////        Intent i = new Intent(this, ChatFragment.class);
////        i.putExtra("productId_key", productId);
////        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        startActivity(i);
//
////        ChatFragment fragmentB = new ChatFragment();
////        Bundle bundle = new Bundle();
////        bundle.putString("NAME", productId);
////        fragmentB.setArguments(bundle);
//
//    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            myId = user.getUid();

        } else {
            startActivity(new Intent(this, NavActivity.class));
            finish();
        }
    }

    @OnClick({R.id.image_user, R.id.send_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_user:
                break;
            case R.id.send_button:
                notify = true;
                String message = messageEdittext.getText().toString().trim();

                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(this, "Cann't send Empty Message", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(message);
                }
                messageEdittext.setText("");
        }
    }

    private void seenMessage() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chating");
//        userRefForSeen = FirebaseDatabase.getInstance().getReference("Products").child(productId).child("Chat");

        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Message message = ds.getValue(Message.class);
                    if (message.getReceiver() == myId
                            && message.getSender() == anotheruserIdInChat) {
                        boolean isSeen = true;
                        userRefForSeen.child(message.getId()).child("isSeen").setValue(isSeen);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readMessage() {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Chating");
//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Chat").child(productId);

//        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Products").child(productId).child("Chat");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
//                    if(message.getProductId().equals(productId)){
//                        messages.add(message);
//                    }
                    //
//                    message.getReceiver().equals(myId)
//                            && message.getSender().equals(getnametoprofile) ||
//                            message.getReceiver().equals(getnametoprofile)
//                                    && message.getSender().equals(myId)  ||
                    //
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    String myIds = user.getUid();
//                    Log.d(TAG, "onDataChange2: " + myIds);
                    Log.d(TAG, "onDataChange22: " + anotheruserIdInChat);

//                    if (anotheruserIdInChat.equals(myId)) {
//                        anotheruserIdInChat = reciverproduct;
////                        Log.d(TAG, "onDataChange22: " + anotheruserIdInChat);
//                    } else {
//
//                    }
                    Log.d(TAG, "onData: " + message.getReceiver());
                    Log.d(TAG, "onData1: " + message.getSender());

                    if (
                            message.getReceiver().equals(myId)
                                    && message.getSender().equals(anotheruserIdInChat) ||
                                    message.getReceiver().equals(anotheruserIdInChat)
                                            && message.getSender().equals(myId)
                                    ||
                                    message.getReceiver().equals(myId)
                                            && message.getSender().equals(getnametoprofile) ||
                                    message.getReceiver().equals(getnametoprofile)
                                            && message.getSender().equals(myId)

//                                message.getReceiver().equals(myId)
//                            && message.getSender().equals(anotheruserIdInChat) ||
//                            message.getReceiver().equals(anotheruserIdInChat)
//                                    && message.getSender().equals(myId)
                    ) {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    String myIds = user.getUid();
//                    if (anotheruserIdInChat == myIds) {
//                        anotheruserIdInChat = reciverproduct;
//                    } else {
//
//                    }

//                    if (
//                            message.getReceiver() == myId
//                                    && message.getSender() == anotheruserIdInChat ||
//                                    message.getReceiver() == anotheruserIdInChat
//                                            && message.getSender() == myId ||
//                                    message.getReceiver() == myId
//                                            && message.getSender() == getnametoprofile ||
//                                    message.getReceiver() == getnametoprofile
//                                            && message.getSender() == myId
//                    ) {

//                    if (message.getProductId().equals(productId)) {
//                        messages.add(message);
//                    }

                        if (message.getProductId().equals(productId)) {
                            messages.add(message);
                        }

                    }

                    adapter = new MessageAdapter(messages, ChatActivity.this, image);
                    adapter.notifyDataSetChanged();
//                    chatRecyclerview.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    chatRecyclerview.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void sendMessage(String message) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();

//
        String totaltimee = null;
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat gethourr = new SimpleDateFormat("HH");
        SimpleDateFormat getminutee = new SimpleDateFormat("mm");
        String hourr = gethourr.format(cc.getTime());
        String minutee = getminutee.format(cc.getTime());
        int convertedVall = Integer.parseInt(hourr);

        if (convertedVall > 12) {
            totaltimee = ((convertedVall - 12) + ":" + (minutee) + "pm");
        } else if (convertedVall == 12) {
            totaltimee = ("12" + ":" + (minutee) + "pm");
        } else if (convertedVall < 12) {
            if (convertedVall != 0) {
                totaltimee = ((convertedVall) + ":" + (minutee) + "am");
            } else {
                totaltimee = ("12" + ":" + minutee + "am");
            }
        }
        //
        String date_nn = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        String t_d = date_nn + "_" + totaltimee;

        //
//        HashMap<String, Object> hashMap = new HashMap<>;
        boolean isSeen = false;
//        if (getnametoprofile == myId ){
//            getnametoprofile = reciverproduct;
//            Message message1 = new Message(Id, message, myId, getnametoprofile, isSeen, productId, t_d);
//            databaseReference.child("Chating").child(Id).setValue(message1);

//        }
//        else {
//            Message message1 = new Message(Id, message, myId, getnametoprofile, isSeen, productId, t_d);
//            databaseReference.child("Chating").child(Id).setValue(message1);

//        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String myIds = user.getUid();
        if (getnametoprofile.equals(myIds)) {
            getnametoprofile = reciverproduct;
        } else {

        }
        Message message1 = new Message(Id, message, myId, getnametoprofile, isSeen, productId, t_d);
        databaseReference.child("Chating").child(Id).setValue(message1);

//        databaseReference.child("Products").child(productId).child("Chat").child(Id).setValue(message1);

        String msg = message;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("User").child(myId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (notify) {
                    sendNotification(anotheruserIdInChat, user.getFullName(), message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String finalTotaltime = totaltime;

//        myRef.child("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

        Query query = FirebaseDatabase.getInstance().getReference("Chats").orderByChild("productId").equalTo(productId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String id = dataSnapshot.getValue().toString();
//                Log.d(TAG, "onDataChange222: "  + id);
                if (dataSnapshot.exists()) {

//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                        Chats chats = ds.getValue(Chats.class);
                    Query querys = FirebaseDatabase.getInstance().getReference("Chats").orderByChild("senderId").equalTo(myId);
                    querys.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot ds) {
                            if (ds.exists()) {

                            } else {
                                Chats sponsor = new Chats(Id, productId, myId, anotheruserIdInChat, date_n, finalTotaltime);
                                databaseReference.child("Chats").child(Id).setValue(sponsor);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Chats sponsor = new Chats(Id, productId, myId, anotheruserIdInChat, date_n, finalTotaltime);
                    databaseReference.child("Chats").child(Id).setValue(sponsor);
//                        Toast.makeText(ChatActivity.this, "m4 mwgod", Toast.LENGTH_SHORT).show();

                }


//                    Chats chats1 = snapshot.getValue(Chats.class);
//                    String id = snapshot.getKey();
//                    assert chats1 != null;
//                    chats1.setRootId(id);
//                    String chatId = chats1.getRootId();
//                    chats1.getSenderId();
//                    chats1.getProductId();
//                    Log.d(TAG, "onDataChangee1: "+chats1.getSenderId());
//                    Log.d(TAG, "onDataChangee2: "+chats1.getProductId());
//
//                    if (chats1.getSenderId()== user.getUid()
//                            && chats1.getProductId() == productId) {
////                        Chats sponsor1 = new Chats(chatId,date_n , finalTotaltime);
////                        databaseReference.child("Chats").child(chatId).setValue(sponsor1);
//
//                        Toast.makeText(ChatActivity.this, " exist", Toast.LENGTH_SHORT).show();
//
//                    } else
//                    {
//                        Chats sponsor = new Chats(Id,productId,myId,anotheruserIdInChat,date_n , finalTotaltime);
//                        databaseReference.child("Chats").child(Id).setValue(sponsor);
//
//                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Chats sponsor = new Chats(Id,productId,myId,anotheruserIdInChat,date_n ,totaltime);
//        databaseReference.child("Chats").child(Id).setValue(sponsor);
    }

    private void sendNotification(String anotheruserIdInChat, String fullName, String message) {

        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(anotheruserIdInChat);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(myId, fullName + ":" + message, "New Message", anotheruserIdInChat, R.drawable.ic_face);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Toast.makeText(ChatActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefForSeen.removeEventListener(seenListener);
    }

    @OnClick(R.id.username_chat_textview)
    public void onViewClicked() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String myIds = user.getUid();
        Intent c = new Intent(ChatActivity.this, AnotherProfileActivity.class);
        if (getnametoprofile.equals(myIds)) {
            getnametoprofile = reciverproduct;
        } else {

        }
        c.putExtra("userId_key", getnametoprofile);
        c.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(c);
    }
}
