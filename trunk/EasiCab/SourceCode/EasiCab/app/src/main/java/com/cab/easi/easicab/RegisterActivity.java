package com.cab.easi.easicab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunayak on 7/22/2016.
 */
public class RegisterActivity extends Activity implements  AdapterView.OnItemSelectedListener {
    static int  role_position = 0;
    Spinner spinner;
    EditText etVnum;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        spinner = (Spinner) findViewById(R.id.sp_select);
        etVnum = (EditText)findViewById(R.id.et_vehNum);
        etVnum.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements for User Shift Details
        List<String> categories_shift = new ArrayList<String>();
        categories_shift.add("Morning Shift");
        categories_shift.add("Afternoon Shift");
        categories_shift.add("Night Shift");


        // Spinner Drop down elements for route details
        List<String> categories_route = new ArrayList<String>();
        categories_route.add("Route One");
        categories_route.add("Route Two");
        categories_route.add("Route Three");


        Intent i = getIntent();
        String _role = i.getStringExtra("selected_role");


        if (_role.equalsIgnoreCase( getResources().getString(R.string.Role_One))) {
            role_position = 0;
            etVnum.setVisibility(View.INVISIBLE);
        }
        else  if (_role.equalsIgnoreCase( getResources().getString(R.string.Role_two))) {
            role_position = 1;
            etVnum.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);


            // Creating adapter for spinner
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_route);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }
        else  if (_role.equalsIgnoreCase( getResources().getString(R.string.Role_three))) {
            role_position = 2;
            spinner.setVisibility(View.VISIBLE);
            etVnum.setVisibility(View.INVISIBLE);
            // Creating adapter for spinner
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_shift);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
        }
       // Toast.makeText(RegisterActivity.this, ""+role_position, Toast.LENGTH_SHORT).show();

      updateRolebyRegistration(role_position);


    }

    private void updateRolebyRegistration(int role_position) {

        switch (role_position) {

             case 0:


             break;

             case 1:


             break;
             case 2:

             break;

        }


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
