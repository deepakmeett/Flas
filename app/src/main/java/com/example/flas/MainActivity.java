package com.example.flas;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button button, button1;
    String s1;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        sharedPreferences = getSharedPreferences( "Database", this.MODE_PRIVATE );
        button = findViewById( R.id.bt1 );
        button1 = findViewById( R.id.bt2 );
        seekBar = findViewById( R.id.seek );
        seekBar.setThumbTintList( ContextCompat.getColorStateList( this, R.color.colorPrimary ) );
        seekBar.setProgressTintList( ContextCompat.getColorStateList( this, R.color.colorPrimary ) );
        
        
        String s2 = sharedPreferences.getString( "a", "" );
        if (s2.equals( "b" )) {
            button.setText( s1 );
            button.setVisibility( View.VISIBLE );
        }
        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = i;
                s1 = (progress * 10) + "%";
                button.setText( s1 );
                button.setVisibility( View.VISIBLE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString( "a", "b" );
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        } );
        Button button = (Button) this.findViewById( R.id.bt1 );
        final MediaPlayer fediaPlayer = MediaPlayer.create( this, R.raw.on_off );
        
        
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fediaPlayer.start();
                    }
                } );
        
        
        button1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, Main3Activity.class );
                startActivity( intent );
            }
        } );

    }
}




















