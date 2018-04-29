package edu.uwf.kennethdale.scavenger_client;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_class);

        List<String> deptNames;
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.dept_spinner);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        /* Spinner click listener */
        spinner.setOnItemSelectedListener(this);

        //Utility utility = new Utility();
        //utility.populateSpinner(this, spinner, "professor", "name");
        String responseText = "";

        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            responseText = getRequest.execute("http://188.166.97.39/department").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        deptNames = new ArrayList<String>();
        try {
            /*JSONArray array = new JSONArray(responseText);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                deptNames.add(resp.getString("name"));
            }*/

            JSONObject obj = new JSONObject(responseText);
            JSONArray array = obj.getJSONArray("results");

            for (int i = 0; i < array.length(); i++){

                deptNames.add(array.getString(i));
            }

        } catch(JSONException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deptNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        String responseText1 = "";
        List<String> courseNames;
        // Spinner element
        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        courseSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        /* Spinner click listener */
        courseSpinner.setOnItemSelectedListener(this);

        HttpGetRequest getRequest1 = new HttpGetRequest();
        try {
            responseText1 = getRequest1.execute("http://188.166.97.39/department/professor/" + deptNames.get(spinner.getSelectedItemPosition())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        courseNames = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray(responseText1);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                courseNames.add(resp.getString("name"));
            }



        } catch(JSONException e){
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courseNames);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(dataAdapter1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
