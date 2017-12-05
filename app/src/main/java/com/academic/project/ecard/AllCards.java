package com.academic.project.ecard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdlions.dto.Profile;
import com.bdlions.dto.response.SignInResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

public class AllCards extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private LinearLayout business_card_layout_1,
                            business_card_layout_2,
                            business_card_layout_3,
                            business_card_layout_4,
                            business_card_layout_5,
                            business_card_layout_6,
                            business_card_layout_7;
        private TextView
                tvLC1FullName, tvLC1JobTitle, tvLC1Cell, tvLC1Email, tvLC1Website, tvLC1Address,
                tvLC2FullName, tvLC2JobTitle, tvLC2Cell, tvLC2Email, tvLC2Website, tvLC2Address,
                tvLC3FullName, tvLC3JobTitle, tvLC3Cell, tvLC3Email, tvLC3Website, tvLC3Address,
                tvLC4FullName, tvLC4JobTitle, tvLC4Cell, tvLC4Email, tvLC4Website, tvLC4Address,
                tvLC5FullName, tvLC5JobTitle, tvLC5Cell, tvLC5Email, tvLC5Website, tvLC5Address,
                tvLC6FullName, tvLC6JobTitle, tvLC6Cell, tvLC6Email, tvLC6Website, tvLC6Address,
                tvLC7FullName, tvLC7JobTitle, tvLC7Cell, tvLC7Email, tvLC7Website, tvLC7Address;
        SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.all_cards_toolbar);
        setSupportActionBar(toolbar);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        tvLC1FullName = (TextView)findViewById(R.id.tv_lc1_full_name);
        tvLC1JobTitle = (TextView)findViewById(R.id.tv_lc1_job_title);
        tvLC1Cell = (TextView)findViewById(R.id.tv_lc1_cell);
        tvLC1Email = (TextView)findViewById(R.id.tv_lc1_full_email);
        tvLC1Website = (TextView)findViewById(R.id.tv_lc1_website);
        tvLC1Address = (TextView)findViewById(R.id.tv_lc1_address);

        tvLC2FullName = (TextView)findViewById(R.id.tv_lc2_full_name);
        tvLC2JobTitle = (TextView)findViewById(R.id.tv_lc2_job_title);
        tvLC2Cell = (TextView)findViewById(R.id.tv_lc2_cell);
        tvLC2Email = (TextView)findViewById(R.id.tv_lc2_full_email);
        tvLC2Website = (TextView)findViewById(R.id.tv_lc2_website);
        tvLC2Address = (TextView)findViewById(R.id.tv_lc2_address);

        tvLC3FullName = (TextView)findViewById(R.id.tv_lc3_full_name);
        tvLC3JobTitle = (TextView)findViewById(R.id.tv_lc3_job_title);
        tvLC3Cell = (TextView)findViewById(R.id.tv_lc3_cell);
        tvLC3Email = (TextView)findViewById(R.id.tv_lc3_full_email);
        tvLC3Website = (TextView)findViewById(R.id.tv_lc3_website);
        tvLC3Address = (TextView)findViewById(R.id.tv_lc3_address);

        tvLC4FullName = (TextView)findViewById(R.id.tv_lc4_full_name);
        tvLC4JobTitle = (TextView)findViewById(R.id.tv_lc4_job_title);
        tvLC4Cell = (TextView)findViewById(R.id.tv_lc4_cell);
        tvLC4Email = (TextView)findViewById(R.id.tv_lc4_full_email);
        tvLC4Website = (TextView)findViewById(R.id.tv_lc4_website);
        tvLC4Address = (TextView)findViewById(R.id.tv_lc4_address);

        tvLC5FullName = (TextView)findViewById(R.id.tv_lc5_full_name);
        tvLC5JobTitle = (TextView)findViewById(R.id.tv_lc5_job_title);
        tvLC5Cell = (TextView)findViewById(R.id.tv_lc5_cell);
        tvLC5Email = (TextView)findViewById(R.id.tv_lc5_full_email);
        tvLC5Website = (TextView)findViewById(R.id.tv_lc5_website);
        tvLC5Address = (TextView)findViewById(R.id.tv_lc5_address);

        tvLC6FullName = (TextView)findViewById(R.id.tv_lc6_full_name);
        tvLC6JobTitle = (TextView)findViewById(R.id.tv_lc6_job_title);
        tvLC6Cell = (TextView)findViewById(R.id.tv_lc6_cell);
        tvLC6Email = (TextView)findViewById(R.id.tv_lc6_full_email);
        tvLC6Website = (TextView)findViewById(R.id.tv_lc6_website);
        tvLC6Address = (TextView)findViewById(R.id.tv_lc6_address);

        tvLC7FullName = (TextView)findViewById(R.id.tv_lc7_full_name);
        tvLC7JobTitle = (TextView)findViewById(R.id.tv_lc7_job_title);
        tvLC7Cell = (TextView)findViewById(R.id.tv_lc7_cell);
        tvLC7Email = (TextView)findViewById(R.id.tv_lc7_full_email);
        tvLC7Website = (TextView)findViewById(R.id.tv_lc7_website);
        tvLC7Address = (TextView)findViewById(R.id.tv_lc7_address);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.all_cards_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onClickBusinessCard1ButtonListener();
        onClickBusinessCard2ButtonListener();
        onClickBusinessCard3ButtonListener();
        onClickBusinessCard4ButtonListener();
        onClickBusinessCard5ButtonListener();
        onClickBusinessCard6ButtonListener();
        onClickBusinessCard7ButtonListener();

        this.initTemplate();

    }

    public void initTemplate()
    {
        String sessionId = session.getSessionId();
        org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
        packetHeader.setAction(ACTION.FETCH_PROFILE_INFO);
        packetHeader.setRequestType(REQUEST_TYPE.REQUEST);
        packetHeader.setSessionId(sessionId);
        new BackgroundWork().execute(packetHeader, "{}", new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg != null)
                {
                    String stringProfile = (String)msg.obj;
                    if(stringProfile != null)
                    {
                        System.out.println(stringProfile);
                        Gson gson = new Gson();
                        Profile profile = gson.fromJson(stringProfile, Profile.class);
                        //SignInResponse signInResponse = gson.fromJson(stringSignInResponse, SignInResponse.class);
                        if(profile.isSuccess())
                        {
                            //set profile info into card template
                            try
                            {
                                //set profile info
                                JSONObject jsonProfileInfo  = new JSONObject(stringProfile);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());
                                JSONObject jsonCompanyInfo  = new JSONObject(jsonProfileInfo.get("company").toString());

                                tvLC1FullName.setText(profile.getUser().getFirstName()+" "+profile.getUser().getLastName());
                                tvLC1JobTitle.setText(profile.getDesignation());
                                tvLC1Cell.setText(profile.getUser().getCell());
                                tvLC1Email.setText(profile.getUser().getEmail());
                                tvLC1Website.setText(profile.getCompany().getWebsite());
                                tvLC1Address.setText(profile.getCompany().getAddress());


                                tvLC2FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC2JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC2Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC2Email.setText(jsonUserInfo.get("email").toString());
                                tvLC2Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC2Address.setText(jsonCompanyInfo.get("address").toString());

                                tvLC3FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC3JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC3Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC3Email.setText(jsonUserInfo.get("email").toString());
                                tvLC3Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC3Address.setText(jsonCompanyInfo.get("address").toString());

                                tvLC4FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC4JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC4Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC4Email.setText(jsonUserInfo.get("email").toString());
                                tvLC4Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC4Address.setText(jsonCompanyInfo.get("address").toString());

                                tvLC5FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC5JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC5Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC5Email.setText(jsonUserInfo.get("email").toString());
                                tvLC5Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC5Address.setText(jsonCompanyInfo.get("address").toString());

                                tvLC6FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC6JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC6Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC6Email.setText(jsonUserInfo.get("email").toString());
                                tvLC6Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC6Address.setText(jsonCompanyInfo.get("address").toString());

                                tvLC7FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                tvLC7JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                tvLC7Cell.setText(jsonUserInfo.get("cell").toString());
                                tvLC7Email.setText(jsonUserInfo.get("email").toString());
                                tvLC7Website.setText(jsonCompanyInfo.get("website").toString());
                                tvLC7Address.setText(jsonCompanyInfo.get("address").toString());
                            }
                            catch (Exception ex)
                            {

                            }
                        }
                        else
                        {
                            AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(AllCards.this);
                            sign_in_builder.setMessage(profile.getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });

                            AlertDialog sign_in_alert = sign_in_builder.create();
                            sign_in_alert.setTitle("Alert!!!");
                            sign_in_alert.show();

                        }
                    }
                    else
                    {
                        //go to mail page
                    }
                }
                else
                {
                    //go to mail page
                }

            }
        });
    }

    public void onClickBusinessCard1ButtonListener(){
        business_card_layout_1 = (LinearLayout)findViewById(R.id.business_card_1);
        business_card_layout_1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout1_intent = new Intent(AllCards.this, SingleCardLayout1.class);
                        startActivity(single_card_layout1_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard2ButtonListener(){
        business_card_layout_2 = (LinearLayout)findViewById(R.id.business_card_2);
        business_card_layout_2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout2_intent = new Intent(AllCards.this, SingleCardLayout2.class);
                        startActivity(single_card_layout2_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard3ButtonListener(){
        business_card_layout_3 = (LinearLayout)findViewById(R.id.business_card_3);
        business_card_layout_3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout3_intent = new Intent(AllCards.this, SingleCardLayout3.class);
                        startActivity(single_card_layout3_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard4ButtonListener(){
        business_card_layout_4 = (LinearLayout)findViewById(R.id.business_card_4);
        business_card_layout_4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout4_intent = new Intent(AllCards.this, SingleCardLayout4.class);
                        startActivity(single_card_layout4_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard5ButtonListener(){
        business_card_layout_5 = (LinearLayout)findViewById(R.id.business_card_5);
        business_card_layout_5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout5_intent = new Intent(AllCards.this, SingleCardLayout5.class);
                        startActivity(single_card_layout5_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard6ButtonListener(){
        business_card_layout_6 = (LinearLayout)findViewById(R.id.business_card_6);
        business_card_layout_6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout6_intent = new Intent(AllCards.this, SingleCardLayout6.class);
                        startActivity(single_card_layout6_intent);
                    }
                }
        );
    }
    public void onClickBusinessCard7ButtonListener(){
        business_card_layout_7 = (LinearLayout)findViewById(R.id.business_card_7);
        business_card_layout_7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_layout7_intent = new Intent(AllCards.this, SingleCardLayout7.class);
                        startActivity(single_card_layout7_intent);
                    }
                }
        );
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_cards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent nav_profile_intent = new Intent(AllCards.this, UserProfile.class);
            startActivity(nav_profile_intent);
        } else if (id == R.id.nav_all_cards) {
            Intent nav_all_cards_intent = new Intent(AllCards.this, AllCards.class);
            startActivity(nav_all_cards_intent);
        } else if (id == R.id.nav_settings) {
            Intent nav_setting_intent = new Intent(AllCards.this, Settings.class);
            startActivity(nav_setting_intent);
        } else if (id == R.id.nav_logout) {
            //clear session
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
