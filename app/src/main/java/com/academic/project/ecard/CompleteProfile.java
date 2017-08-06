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

import com.auction.dto.response.SignInResponse;
import com.auction.util.ACTION;
import com.auction.util.REQUEST_TYPE;
import com.google.gson.Gson;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

public class CompleteProfile extends AppCompatActivity {
    private static Button btnSelectCard;
    private EditText etCell, etDepartment, etJobTitle;
    SessionManager session;
    public static String strProfileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        etCell = (EditText) findViewById(R.id.et_cell);
        etDepartment = (EditText) findViewById(R.id.et_department);
        etJobTitle = (EditText) findViewById(R.id.et_job_title);

        btnSelectCard = (Button)findViewById(R.id.btn_submit_complete_profile);

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
                            }
                            catch(Exception ex)
                            {

                            }
                        }
                        else
                        {
                            AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(CompleteProfile.this);
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

    public void onClickSelectCardButtonListener()
    {
        btnSelectCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            JSONObject jsonProfileInfo  = new JSONObject(strProfileInfo);
                            JSONObject jsonUserInfo  = new JSONObject(jsonProfileInfo.get("user").toString());

                            jsonUserInfo.put("cell", etCell.getText().toString());

                            jsonProfileInfo.put("department", etDepartment.getText().toString());
                            jsonProfileInfo.put("designation", etJobTitle.getText().toString());

                            jsonProfileInfo.put("user", jsonUserInfo);

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
                                                Intent select_card_intent = new Intent(CompleteProfile.this, AllCards.class);
                                                startActivity(select_card_intent);
                                            }
                                            else
                                            {
                                                AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(CompleteProfile.this);
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
                            System.out.println(ex.toString());
                        }

                    }
                }
        );
    }
}
