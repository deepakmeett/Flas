package com.example.flas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class Main3Activity extends AppCompatActivity {

    EditText editText3, editText4;
    Button button3;
    TextView account;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main3 );
        editText3 = findViewById( R.id.edit_email );
        editText4 = findViewById( R.id.edit_password );
        account = findViewById( R.id.account );
        button3 = findViewById( R.id.button_login );
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent( Main3Activity.this, Main4Activity.class );
            startActivity( intent );
            finish();
        }
        sharedPreferences = getSharedPreferences( "DATABASE", MODE_PRIVATE );
        button3.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent( Main3Activity.this, Main2Activity.class );
                startActivity( intent );
                finish();
                return false;
            }
        } );
        account.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), Main2Activity.class );
                startActivity( intent );
            }
        } );
        String s1 = sharedPreferences.getString( "a", "" );
        if (s1.equals( "b" )) {
            editText3.setText( editText3.getText().toString() );
            editText4.setText( editText4.getText().toString() );
        }
        button3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString( "a", "b" );
                editor.apply();
                String email = editText3.getText().toString();
                String password = editText4.getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText( Main3Activity.this,
                                    "Fields are blank", Toast.LENGTH_SHORT ).show();
                } else {
                    login( email, password );
                }
            }
        } );
    }

    private void login(final String emailTwo, String passTwo) {
        mAuth.signInWithEmailAndPassword( emailTwo, passTwo )
                .addOnCompleteListener( Main3Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( Main3Activity.this,
                                            "Login Successfully", Toast.LENGTH_SHORT ).show();
                            Intent intent = new Intent( Main3Activity.this, Main4Activity.class );
                            intent.putExtra( "email", emailTwo );
                            startActivity( intent );

                        } else {
                            Toast.makeText( Main3Activity.this,
                                            "Not Successfully", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }

}
