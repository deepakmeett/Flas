package com.example.flas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class Main4Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    UserAdapter adapter;
    List<User> mUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main4 );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = findViewById( R.id.progress_circular );
        recyclerView = findViewById( R.id.userList );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        mUser = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue( User.class );
//                    if (!user.getId().equals( firebaseUser.getUid() )) {
                        mUser.add( user );
                        progressBar.setVisibility( View.INVISIBLE );
//                    }
                }
                adapter = new UserAdapter( Main4Activity.this, mUser );
                recyclerView.setAdapter( adapter );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
        
//        sendUIDtoFireBase();
    }
    
    private void sendUIDtoFireBase(){
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        
        if (!(currentUser == null)){
            Intent intent = new Intent(  );
            String  emailFORUid = intent.getStringExtra( "email" );
            databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" ) .child("uid");
            databaseReference.setValue( currentUser.getUid() ).addOnCompleteListener( new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText( Main4Activity.this, "UID sent", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_bar, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            firebaseAuth.signOut();
            Toast.makeText( this, "logout successfully", Toast.LENGTH_SHORT ).show();
            onBackPressed();
        }
        return true;
    }
}
