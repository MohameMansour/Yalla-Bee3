package com.example.yallabee3.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA6MkukZs:APA91bGaZSEL7Kr_Hffy9luVV5qhb1NDxpMVqpDTLkkNjiJQ0OHVmzs_peQinoSOCmqwXWrYNEpg5u8h2aBLFQOIFQnn72L_qt66u2vtRuhMhgkJG7vyV-Fjr9mhU-Jo47jdk4RCvLIs"
    })
    @POST("fcm/send")
    Call<Response> sendNotification (@Body Sender body);

}
