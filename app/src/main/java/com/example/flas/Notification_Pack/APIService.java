package com.example.flas.Notification_Pack;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {
    
    @Headers( 
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAZ7V4bd8:APA91bHjQxZz2-Qt--RfWlIxiIaCY83_QjyTJwXCXSs7MJ06XWxG1bxKorGFLqNIwKt4mv8mJqHct5hO909ZvxKuLl73ySd2cWZ60AGYK_PErLE8dGf63x5f2qLqkIV4OwPbPelf67y5"
            }
    )
    
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
    
}
