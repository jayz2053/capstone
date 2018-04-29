package edu.uwf.kennethdale.scavenger_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class QRResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrresult);
        String[] temp;

        Intent prevScreen = getIntent();
        Bundle b = prevScreen.getExtras();
        String scanResult = b.getString("SCAN_RESULT");
        TextView test = (TextView) findViewById(R.id.textView5);

        temp = scanResult.split("/");

        String responseText = "";

        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            responseText = getRequest.execute("http://188.166.97.39/destination/" + temp[4]).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String type = "", description = "", str = "";
        try {
            JSONArray array = new JSONArray(responseText);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                type = resp.getString("destType");
                description = resp.getString("description");

                str += ("Building:  4\n" + "Room number:  " + temp[4]
                        + "\n" + type + "\n" + description);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        test.setText(str);


        Button searchProfessor_button = findViewById(R.id.homeScreen_button);
        searchProfessor_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ goToHomeScreen();
            }
        });
   }

    private void goToHomeScreen(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
