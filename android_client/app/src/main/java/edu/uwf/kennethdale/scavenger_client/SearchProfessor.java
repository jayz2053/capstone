package edu.uwf.kennethdale.scavenger_client;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class SearchProfessor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_professor);
        List<String> profNames;
        List<String> profEmails;
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.professor_spinner);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        /* Spinner click listener */
        spinner.setOnItemSelectedListener(this);

        //Utility utility = new Utility();
        //utility.populateSpinner(this, spinner, "professor", "name");
        String responseText = "";

        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            responseText = getRequest.execute("http://188.166.97.39/professor").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        profNames = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray(responseText);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                profNames.add(resp.getString("name"));
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, profNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
