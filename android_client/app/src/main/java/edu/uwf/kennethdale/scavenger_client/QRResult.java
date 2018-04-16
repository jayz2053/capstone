package edu.uwf.kennethdale.scavenger_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QRResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrresult);

        Intent prevScreen = getIntent();
        Bundle b = prevScreen.getExtras();
        String scanResult = b.getString("SCAN_RESULT");
        TextView test = (TextView) findViewById(R.id.textView5);
        test.setText(scanResult);
    }
}
