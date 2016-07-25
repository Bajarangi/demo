package com.cab.easi.easicab;

/**
 * Created by sunayak on 7/22/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {
    TextView registerScreen;
    ImageView ivLogoclick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_login);

        registerScreen = (TextView) findViewById(R.id.link_to_register);
        ivLogoclick = (ImageView)findViewById(R.id.logoClick);

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), SelectRole.class);
                startActivity(i);
            }
        });

        ivLogoclick.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);
            }
        });
    }
}