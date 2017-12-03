package com.academic.project.ecard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlions.dto.Profile;
import com.bdlions.dto.response.SignInResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    private static Button button_select_card;
    private EditText etFirstName, etLastName, etCompany, etJobTitle, etAddress, etCell, etWebsite;
    SessionManager session;
    public static String strProfileInfo;
    public Profile profileInfo = new Profile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        etFirstName = (EditText) findViewById(R.id.et_user_first_name);
        etLastName = (EditText) findViewById(R.id.et_user_last_name);
        etCompany = (EditText) findViewById(R.id.et_company);
        etJobTitle = (EditText) findViewById(R.id.et_job_title);
        etAddress = (EditText) findViewById(R.id.et_address);
        etCell = (EditText) findViewById(R.id.et_cell);
        etWebsite = (EditText) findViewById(R.id.et_website);


        onClickEditProfileButtonListener();

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
                        if(profile.isSuccess())
                        {
                            try
                            {
                                profileInfo = profile;
                                strProfileInfo = stringProfile;
                                //set profile info
                                JSONObject jsonProfileInfo  = new JSONObject(stringProfile);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());
                                JSONObject jsonCompanyInfo  = new JSONObject(jsonProfileInfo.get("company").toString());
                                System.out.println(jsonProfileInfo);

                                etFirstName.setText(profile.getUser().getFirstName());
                                etLastName.setText(profile.getUser().getLastName());
                                etCompany.setText(profile.getCompany().getTitle());
                                etJobTitle.setText(profile.getDesignation());
                                //etAddress.setText(profile.getAddress);
                                etCell.setText(profile.getUser().getCell());
                                //etWebsite.setText(profile.getWebsite());


                            }
                            catch(Exception ex)
                            {

                            }
                        }
                        else
                        {
                            AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(EditProfile.this);
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

    public void onClickEditProfileButtonListener(){
        button_select_card = (Button)findViewById(R.id.select_card_button);
        button_select_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(profileInfo != null && profileInfo.getId() > 0)
                        {
                            try
                            {
                                JSONObject jsonProfileInfo  = new JSONObject(strProfileInfo);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());

                                profileInfo.getUser().setFirstName(etFirstName.getText().toString());
                                profileInfo.getUser().setLastName(etLastName.getText().toString());
                                profileInfo.getCompany().setTitle(etCompany.getText().toString());
                                profileInfo.setDesignation(etJobTitle.getText().toString());
                                //profileInfo.setAddress(etAddress.getText().toString());
                                profileInfo.getUser().setCell(etCell.getText().toString());
                                //profileInfo.setWebsite(etWebsite.getText().toString());


                                jsonProfileInfo.put("user", jsonUserInfo.toString());

                                org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
                                packetHeader.setAction(ACTION.UPDATE_PROFILE_INFO);
                                packetHeader.setRequestType(REQUEST_TYPE.UPDATE);
                                packetHeader.setSessionId(session.getSessionId());
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                String profileInfoString = gson.toJson(profileInfo);
                                new BackgroundWork().execute(packetHeader, profileInfoString, new Handler(){
                                    @Override
                                    public void handleMessage(Message msg) {
                                        if(msg != null )
                                        {
                                            String stringSignInResponse = (String)msg.obj;
                                            if(stringSignInResponse != null)
                                            {
                                                //Toast.makeText(getApplicationContext(), stringSignInResponse, Toast.LENGTH_LONG).show();
                                                System.out.println(stringSignInResponse);
                                                Gson gson = new Gson();
                                                SignInResponse signInResponse = gson.fromJson(stringSignInResponse, SignInResponse.class);
                                                if(signInResponse.isSuccess())
                                                {
                                                    Intent select_card_intent = new Intent(EditProfile.this, UserProfile.class);
                                                    startActivity(select_card_intent);
                                                }
                                                else
                                                {
                                                    AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(EditProfile.this);
                                                    sign_in_builder.setMessage(signInResponse.getMessage())
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
                            catch(Exception ex)
                            {

                            }

                        }
                        else
                        {
                            //show pop with error message
                        }
                    }
                }
        );
    }
}
