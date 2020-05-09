package com.example.flas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
public class Main2Activity extends AppCompatActivity {

    EditText editText1, editText2, editText3;
    Button button;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    HashMap<String, String> takeData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        editText1 = findViewById( R.id.email );
        editText2 = findViewById( R.id.password );
        editText3 = findViewById( R.id.reenter );
        button = findViewById( R.id.buttonPanel );
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" );

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent( Main2Activity.this, Main3Activity.class );
            startActivity( intent );
            finish();
        }
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editText1.getText().toString();
                String password = editText2.getText().toString();
                String s3 = editText3.getText().toString();
                if (email.isEmpty() && password.isEmpty() && s3.isEmpty()) {
                    Toast.makeText( Main2Activity.this,
                                    "Fields are blank", Toast.LENGTH_SHORT ).show();
                } else if (!password.equals( s3 )) {
                    Toast.makeText( Main2Activity.this,
                                    "Password doesn't match", Toast.LENGTH_SHORT ).show();
                } else {
                    createUser( email, password );
                }
            }
        } );
    }

    private void createUser(final String emailOne, String passOne) {
        mAuth.createUserWithEmailAndPassword( emailOne, passOne )
                .addOnCompleteListener( Main2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( Main2Activity.this,
                                            "Registered Successfully", Toast.LENGTH_SHORT ).show();
                            addData( emailOne );
                            Intent intent = new Intent( Main2Activity.this, Main3Activity.class );
                            startActivity( intent );
                        } else {
                            Toast.makeText( Main2Activity.this,
                                            "Not Successfully", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }

    private void addData(final String emailString) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener( new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    takeData.put( "email", emailString );
                    takeData.put( "uid", "mAuth.getCurrentUser().getUid()" );
                    takeData.put( "token", token );
                }
                databaseReference.push().setValue( takeData ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( getApplicationContext()
                                    , "Text sent on Firebase", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( getApplicationContext()
                                    , "Text not sent on Firebase", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            }
        } );
    }

}
