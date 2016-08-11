package com.cab.easi.easicab;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author  Subhalaxmi
 *
 */
public class MainHomeActivity extends   AppCompatActivity implements View.OnClickListener{
    DrawerArrowDrawable drawerArrow;
    public static DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    LinearLayout    _profile,_DrvList,_EmployeeList,_settings,_logs,_map,_more,_logout;
    TextView  tv_loginuser, tv_profile,tv_drvlist,tv_emplist,tv_setting,tv_log,tv_map,tv_logout;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    public static View mHostFragment ;
    private Button btn_hamburger;
    DisplayMetrics dm;
    int widths;
    RelativeLayout	back_dim_layout;



    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_navigationdrawer);


        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        widths = dm.widthPixels;
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(" ");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_nav_icon);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent_bg)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initializeallstuff();

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }

        };

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                toolbar, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        )
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                //   invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                //  invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //mDrawerLayout.getWidth()
                float xPositionOpenDrawer = mDrawerLayout.getWidth()-widths/3;
                float xPositionWindowContent = (slideOffset * xPositionOpenDrawer);
                mHostFragment.setX(xPositionWindowContent);
                toolbar.setX(xPositionWindowContent);
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerLayout.setDrawerShadow(R.drawable.ic_drawer_img , GravityCompat.START);
        //  mDrawerToggle.syncState();


        if (savedInstanceState == null) {



         //   getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Firstbed_Fragment(wifinewname ,presetpositioname )).commit();

        }
        else {
            // on first time display view for first nav item
          //  getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Basicmode_Fragment()).commit();

        }

    }


    private void initializeallstuff() {
        // TODO Auto-generated method stub

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);

        // basicmode = (LinearLayout) findViewById(R.id.llDrawer_profile);


        tv_profile = (TextView)findViewById(R.id.tv__profile);
        tv_drvlist = (TextView)findViewById(R.id.tv__driverlists);
        tv_emplist = (TextView)findViewById(R.id.tv__emplist);
        tv_setting = (TextView)findViewById(R.id.tv__set);
        tv_log = (TextView)findViewById(R.id.tv__logreports);
        tv_map = (TextView)findViewById(R.id.tv__map);
        tv_logout = (TextView)findViewById(R.id.tv__logout);
        tv_loginuser = (TextView)findViewById(R.id.textViewMainNavUserName);


        Typeface face=Typeface.createFromAsset(getAssets(),"eagan.ttf");

        tv_loginuser.setTypeface(face);

        tv_profile.setTypeface(face);
        tv_drvlist.setTypeface(face);
        tv_emplist.setTypeface(face);
        tv_setting.setTypeface(face);
        tv_log.setTypeface(face);
        tv_map.setTypeface(face);
        tv_logout.setTypeface(face);

        mHostFragment = findViewById(R.id.content_frame);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);
        _profile = (LinearLayout) findViewById(R.id.llDrawer_profile);
        _DrvList = (LinearLayout) findViewById(R.id.llDrawer_driverlist);
        _EmployeeList = (LinearLayout)findViewById(R.id.llDrawer_emplist);
        _settings = (LinearLayout) findViewById(R.id.llDrawer_settings);
        _logs = (LinearLayout)findViewById(R.id.llDrawer_logs);
        _map = (LinearLayout) findViewById(R.id.llDrawer_map);
       // _more = (LinearLayout)findViewById(R.id.llDrawer_more);
        _logout = (LinearLayout) findViewById(R.id.llDrawer_logout);
        _profile.setOnClickListener(this);
        _map.setOnClickListener(this);
        _logout.setOnClickListener(this);

       // btn_hamburger = (Button)findViewById(R.id.ham_bugernotfication_btn);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.startingpoint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public void onClick(View v)
    {

        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        switch(v.getId())
        {
            case R.id.llDrawer_profile:
                Toast.makeText(MainHomeActivity.this, "clicked!!" , Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.llDrawer_map:
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.llDrawer_logout:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
                break;

        }
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent= new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }

}
