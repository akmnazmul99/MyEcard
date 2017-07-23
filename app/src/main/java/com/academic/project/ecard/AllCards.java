package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
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

public class AllCards extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private LinearLayout business_card_layout_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.all_cards_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.all_cards_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onClickBusinessCard1ButtonListener();

    }
    public void onClickBusinessCard1ButtonListener(){
        business_card_layout_1 = (LinearLayout)findViewById(R.id.business_card_1);
        business_card_layout_1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single_card_intent = new Intent(AllCards.this, SingleContact.class);
                        startActivity(single_card_intent);
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
        }
        else if (id == R.id.nav_all_cards) {
            Intent nav_all_cards_intent = new Intent(AllCards.this, AllCards.class);
            startActivity(nav_all_cards_intent);
        }
        else if (id == R.id.nav_settings) {
            Intent nav_setting_intent = new Intent(AllCards.this, Settings.class);
            startActivity(nav_setting_intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
