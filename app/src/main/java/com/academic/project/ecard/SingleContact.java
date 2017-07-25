package com.academic.project.ecard;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;

public class SingleContact extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {
    private ImageView imageViewRound;
    private static String[] items = {"Email","Linkedin","Print"};
    private Button button_open_dialog;
    AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.single_contact_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.single_card_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imageViewRound =(ImageView)findViewById(R.id.contact_imageView_round_1);

        button_open_dialog = (Button)findViewById(R.id.b_open_dialog_share_box);
        button_open_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
            AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("Share via");
        builder.setItems(items, this);

        builder.setNegativeButton("Cancel", null);
        ad = builder.create();


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
        getMenuInflater().inflate(R.menu.single_contact, menu);
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
            Intent nav_profile_intent = new Intent(SingleContact.this, UserProfile.class);
            startActivity(nav_profile_intent);
        } else if (id == R.id.nav_all_cards) {
            Intent nav_all_cards_intent = new Intent(SingleContact.this, AllCards.class);
            startActivity(nav_all_cards_intent);
        } else if (id == R.id.nav_settings) {
            Intent nav_setting_intent = new Intent(SingleContact.this, Settings.class);
            startActivity(nav_setting_intent);
        } else if (id == R.id.nav_logout) {
            Intent nav_logout_intent = new Intent(SingleContact.this, Login.class);
            startActivity(nav_logout_intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int pos) {
        switch( pos )
        {
            case 0:
                Intent email_intent = new Intent(SingleContact.this, EmailCompose.class);
                startActivityForResult(email_intent, 0);
                break;
            case 1:
                Intent linkedin_intent = new Intent(SingleContact.this, Linkedin.class);
                startActivityForResult(linkedin_intent, 0);
                break;
            case 2:
                Intent print_intent = new Intent(SingleContact.this, PrintCard.class);
                startActivityForResult(print_intent, 0);
                break;
        }
    }
}

