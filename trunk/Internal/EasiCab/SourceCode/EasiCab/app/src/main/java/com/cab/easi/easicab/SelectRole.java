package com.cab.easi.easicab;

/**
 * Created by sunayak on 7/22/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectRole extends ActionBarActivity {

        private RadioGroup radioRole;
        private RadioButton radioSelectedButton;
        private Button btnDisplay;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_selectrole);
            radioRole=(RadioGroup)findViewById(R.id.radioRole);

            btnDisplay=(Button)findViewById(R.id.btnOk);

            btnDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId=radioRole.getCheckedRadioButtonId();
                    radioSelectedButton=(RadioButton)findViewById(selectedId);

                    Intent intent = new Intent(SelectRole.this, RegisterActivity.class);
                    intent.putExtra("selected_role" ,radioSelectedButton.getText().toString() );
                    startActivity(intent);
                    finish();
                   // Toast.makeText(SelectRole.this,radioSelectedButton.getText(),Toast.LENGTH_SHORT).show();
                }
            });
        }


    }