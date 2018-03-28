package edu.uwf.kennethdale.scavenger_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Button button = findViewById(R.id.qr_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToNextActivity();
            }
        });
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, QRScanner.class);
        startActivity(intent);
    }
}
