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

    List<String> profEmails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_professor);
        List<String> profNames;

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
        profEmails = new ArrayList<String>();
        try {
            JSONArray array = new JSONArray(responseText);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                profNames.add(resp.getString("name"));
                String[] temp = resp.getString("email").split("@");
                profEmails.add(temp[0]);
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
        String responseText1 = "";
        TextView test = (TextView) findViewById(R.id.textView5);
        HttpGetRequest getRequest1 = new HttpGetRequest();
        try {
            responseText1 = getRequest1.execute("http://188.166.97.39/professor/office/" + profEmails.get(position)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String office, days, startTime, endTime, str = "";
        try {
            JSONArray array = new JSONArray(responseText1);

            for (int i = 0; i < array.length(); i++){
                JSONObject resp = array.getJSONObject(i);
                office = resp.getString("did");
                days = resp.getString("days");
                startTime = resp.getString("start_time");
                endTime = resp.getString("end_time");

                str += ("Office Location:  " + office + "\nDays:  " + days
                        + "\nHours:  " + startTime + " - " + endTime + "\n\n");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        test.setText(str);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
