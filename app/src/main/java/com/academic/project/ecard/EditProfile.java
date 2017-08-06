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

import com.auction.dto.response.SignInResponse;
import com.auction.util.ACTION;
import com.auction.util.REQUEST_TYPE;
import com.google.gson.Gson;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    private static Button button_select_card;
    private EditText etFirstName, etLastName, etCell, etDepartment, etJobTitle;
    SessionManager session;
    public static String strProfileInfo;
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
        etCell = (EditText) findViewById(R.id.et_cell);
        etDepartment = (EditText) findViewById(R.id.et_department);
        etJobTitle = (EditText) findViewById(R.id.et_job_title);

        onClickSelectCardButtonListener();

        this.initTemplate();
    }

    public void initTemplate()
    {
        String sessionId = session.getSessionId();
        org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
        packetHeader.setAction(ACTION.FETCH_LOCATION_LIST);
        packetHeader.setRequestType(REQUEST_TYPE.REQUEST);
        packetHeader.setSessionId(sessionId);
        new BackgroundWork().execute(packetHeader, "{}", new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg != null)
                {
                    String stringSignInResponse = (String)msg.obj;
                    if(stringSignInResponse != null)
                    {
                        System.out.println(stringSignInResponse);
                        Gson gson = new Gson();
                        SignInResponse signInResponse = gson.fromJson(stringSignInResponse, SignInResponse.class);
                        if(signInResponse.isSuccess())
                        {
                            try
                            {
                                strProfileInfo = stringSignInResponse;
                                //set profile info
                                JSONObject jsonProfileInfo  = new JSONObject(stringSignInResponse);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());
                                JSONObject jsonCompanyInfo  = new JSONObject(jsonProfileInfo.get("company").toString());
                                System.out.println(jsonProfileInfo);
                                etFirstName.setText(jsonUserInfo.get("firstName").toString());
                                etLastName.setText(jsonUserInfo.get("lastName").toString());
                                etCell.setText(jsonUserInfo.get("cell").toString());
                                etDepartment.setText(jsonProfileInfo.get("department").toString());
                                etJobTitle.setText(jsonProfileInfo.get("designation").toString());
                            }
                            catch(Exception ex)
                            {

                            }
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

    public void onClickSelectCardButtonListener(){
        button_select_card = (Button)findViewById(R.id.select_card_button);
        button_select_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(strProfileInfo != null && !strProfileInfo.equals(""))
                        {
                            try
                            {
                                JSONObject jsonProfileInfo  = new JSONObject(strProfileInfo);
                                JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());

                                jsonUserInfo.put("firstName", etFirstName.getText());
                                jsonUserInfo.put("lastName", etLastName.getText());
                                jsonUserInfo.put("cell", etCell.getText());

                                jsonProfileInfo.put("department", etDepartment.getText());
                                jsonProfileInfo.put("designation", etJobTitle.getText());

                                jsonProfileInfo.put("user", jsonUserInfo.toString());

                                org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
                                packetHeader.setAction(ACTION.FETCH_PRODUCT_INFO);
                                packetHeader.setRequestType(REQUEST_TYPE.UPDATE);
                                new BackgroundWork().execute(packetHeader, jsonProfileInfo.toString(), new Handler(){
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

                    }
                }
        );
    }
}
