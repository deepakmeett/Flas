package com.example.flas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flas.Notification_Pack.APIService;
import com.example.flas.Notification_Pack.Client;
import com.example.flas.Notification_Pack.Data;
import com.example.flas.Notification_Pack.MyResponse;
import com.example.flas.Notification_Pack.Sender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Notification_5Activity extends AppCompatActivity {

    Button button;
    APIService apiService;
    EditText editTextEmail, editTextUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main5 );
        button = findViewById( R.id.sendNotification );
        editTextEmail = findViewById( R.id.userEmail );
        editTextUid = findViewById( R.id.userId );
        
        apiService = Client.getClient( "https://fcm.googleapis.com/" ).create( APIService.class );
        
        updateToken();
        
        Intent intent = getIntent();
        String email = intent.getStringExtra( "emailA" );
        String uid = intent.getStringExtra( "uid" );

        editTextEmail.setText( email );
        editTextEmail.setEnabled( false );
        editTextUid.setText( uid );
        editTextUid.setEnabled( false);
        
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child( "Tokens" )
                        .child( editTextUid.getText().toString().trim()).child( "token" )
                        .addListenerForSingleValueEvent( new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String userToken = dataSnapshot.getValue(String.class);
                            sendNotification( userToken );
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        } );
            }
        } );
    }
    
    private void updateToken(){
        String refreshToken = FirebaseInstanceId.getInstance().getToken(  );
        Token token = new Token( refreshToken );
        FirebaseDatabase.getInstance().getReference( "Tokens")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue( token );
        
    }
    
    public void sendNotification(String userToken){
        Data data = new Data( "title", "message" );
        Sender sender =  new Sender( data, userToken );
        apiService.sendNotification( sender ).enqueue( new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200){
                    if (response.body()==null){
                        Toast.makeText( Notification_5Activity.this, "Response Body is null"
                                , Toast.LENGTH_SHORT ).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText( Notification_5Activity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
