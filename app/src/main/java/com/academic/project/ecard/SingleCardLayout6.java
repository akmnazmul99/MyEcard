package com.academic.project.ecard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bdlions.dto.Profile;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.auction.udp.BackgroundUploader;
import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class SingleCardLayout6 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnClickListener {
    SessionManager session;
    private static String[] items = {"Facebook", "Linkedin", "Email"};
    private Button button_open_dialog;
    AlertDialog ad;
    public Dialog imageUploadDialog;
    public int imgUploadType;

    public String imgName = "";

    private TextView tvLC6FullName, tvLC6JobTitle, tvLC6Cell, tvLC6Email, tvLC6Website, tvLC6Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_card_layout6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvLC6FullName = (TextView)findViewById(R.id.tv_lc6_full_name);
        tvLC6JobTitle = (TextView)findViewById(R.id.tv_lc6_job_title);
        tvLC6Cell = (TextView)findViewById(R.id.tv_lc6_cell);
        tvLC6Email = (TextView)findViewById(R.id.tv_lc6_full_email);
        tvLC6Website = (TextView)findViewById(R.id.tv_lc6_website);
        tvLC6Address = (TextView)findViewById(R.id.tv_lc6_address);

        session = new SessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.single_card_layout6_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        builder.setTitle("Share via");
        builder.setItems(items, this);

        builder.setNegativeButton("Cancel", null);
        ad = builder.create();
        imageUploadDialog = new Dialog(SingleCardLayout6.this);
        this.initTemplate();
        this.onClickShareCardButtonListener();
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
                        if(profile.isSuccess())
                        {
                            //set profile info into card template
                            try
                            {
                                //set profile info
                                JSONObject jsonProfileInfo  = new JSONObject(stringProfile);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());
                                JSONObject jsonCompanyInfo  = new JSONObject(jsonProfileInfo.get("company").toString());

                                tvLC6FullName.setText(profile.getUser().getFirstName()+" "+profile.getUser().getLastName());
                                tvLC6JobTitle.setText(profile.getDesignation());
                                tvLC6Cell.setText(profile.getUser().getCell());
                                tvLC6Email.setText(profile.getUser().getEmail());
                                tvLC6Website.setText(profile.getCompany().getWebsite());
                                tvLC6Address.setText(profile.getCompany().getAddress());

                                //tvLC1FullName.setText(jsonUserInfo.get("firstName").toString()+" "+jsonUserInfo.get("lastName").toString());
                                //tvLC1JobTitle.setText(jsonProfileInfo.get("designation").toString());
                                //tvLC1Cell.setText(jsonUserInfo.get("cell").toString());
                                //tvLC1Email.setText(jsonUserInfo.get("email").toString());
                                //tvLC1Website.setText(jsonCompanyInfo.get("website").toString());
                                //tvLC1Address.setText(jsonCompanyInfo.get("address").toString());
                            }
                            catch (Exception ex)
                            {

                            }
                        }
                        else
                        {

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

    public void onClickShareCardButtonListener()
    {
        button_open_dialog = (Button)findViewById(R.id.b_open_dialog_share_box);
        button_open_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUploadDialog.show();
                LinearLayout idCardDesign = (LinearLayout)findViewById(R.id.idCardDesign6);
                idCardDesign.setDrawingCacheEnabled(true);
                idCardDesign.buildDrawingCache(true);
                Bitmap bmp = Bitmap.createBitmap(idCardDesign.getDrawingCache());
                idCardDesign.setDrawingCacheEnabled(false);
                final String filePath = saveToInternalStorage(bmp);
                try {
                    new BackgroundUploader().execute(filePath, new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            imageUploadDialog.dismiss();
                            try
                            {
                                if(msg != null)
                                {
                                    ad.show();
                                }
                            }
                            catch(Exception ex)
                            {
                                Toast.makeText(getApplicationContext(), "Unable to save image. Please try again later.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

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
        getMenuInflater().inflate(R.menu.single_card_layout6, menu);
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
            Intent nav_profile_intent = new Intent(SingleCardLayout6.this, UserProfile.class);
            startActivity(nav_profile_intent);
        } else if (id == R.id.nav_all_cards) {
            Intent nav_all_cards_intent = new Intent(SingleCardLayout6.this, AllCards.class);
            startActivity(nav_all_cards_intent);
        } else if (id == R.id.nav_settings) {
            Intent nav_setting_intent = new Intent(SingleCardLayout6.this, Settings.class);
            startActivity(nav_setting_intent);
        } else if (id == R.id.nav_logout) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int pos) {
        //imageUploadDialog = new Dialog(SingleCardLayout1.this);
        switch( pos )
        {
            case 0:
                //logic to share card in facebook
                if(imgName == null || imgName.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please save your card.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent facebookIntent = new Intent(SingleCardLayout6.this, Facebook.class);
                    facebookIntent.putExtra("imgName", imgName);
                    startActivityForResult(facebookIntent, 0);
                    return;
                }
                break;
            case 1:
                //logic to share card in linkedin
                if(imgName == null || imgName.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please save your card.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent linkedinIntent = new Intent(SingleCardLayout6.this, Linkedin.class);
                    linkedinIntent.putExtra("imgName", imgName);
                    startActivityForResult(linkedinIntent, 0);
                    return;
                }
                break;
            case 2:
                //logic to send card via email
                Intent emailIntent = new Intent(SingleCardLayout6.this, EmailCompose.class);
                emailIntent.putExtra("imgName", imgName);
                startActivityForResult(emailIntent, 0);
                break;
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        UUID random = UUID.randomUUID();
        imgName = random + ".png";
        // Create imageDir
        File mypath=new File(directory, random + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mypath.getAbsolutePath();
    }
}
