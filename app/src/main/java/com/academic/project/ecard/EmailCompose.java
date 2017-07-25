package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class EmailCompose extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.email_compose_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.email_compose_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        getMenuInflater().inflate(R.menu.email_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent nav_profile_intent = new Intent(EmailCompose.this, UserProfile.class);
            startActivity(nav_profile_intent);
        } else if (id == R.id.nav_all_cards) {
            Intent nav_all_cards_intent = new Intent(EmailCompose.this, AllCards.class);
            startActivity(nav_all_cards_intent);
        } else if (id == R.id.nav_settings) {
            Intent nav_setting_intent = new Intent(EmailCompose.this, Settings.class);
            startActivity(nav_setting_intent);
        } else if (id == R.id.nav_logout) {
            Intent nav_logout_intent = new Intent(EmailCompose.this, Login.class);
            startActivity(nav_logout_intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



//How to send an email with a file attachment in Android
//url: http://stackoverflow.com/questions/9974987/how-to-send-an-email-with-a-file-attachment-in-android
//String filename="contacts_sid.vcf";
//File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
//Uri path = Uri.fromFile(filelocation);
//Intent emailIntent = new Intent(Intent.ACTION_SEND);
//// set the type to 'email'
//emailIntent .setType("vnd.android.cursor.dir/email");
//        String to[] = {"asd@gmail.com"};
//        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
//// the attachment
//        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
//// the mail subject
//        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
//        startActivity(Intent.createChooser(emailIntent , "Send email..."));

//Tutorial: https://www.youtube.com/watch?v=Z7lgmFF2WP8
