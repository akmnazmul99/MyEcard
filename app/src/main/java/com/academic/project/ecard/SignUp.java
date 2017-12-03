package com.academic.project.ecard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bdlions.dto.Profile;
import com.bdlions.dto.User;
import com.bdlions.dto.response.SignInResponse;
import com.bdlions.util.ACTION;
import com.bdlions.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    private static Button button_reg;
    SessionManager session;
    private EditText etFirstName, etLastName, etEmail, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        etFirstName = (EditText) findViewById(R.id.et_sign_up_first_name);
        etLastName = (EditText) findViewById(R.id.et_sign_up_last_name);
        etEmail = (EditText) findViewById(R.id.et_sign_up_email);
        etPassword= (EditText) findViewById(R.id.et_sign_up_password);

        onClickSignUpButtonListener();
    }
    public void onClickSignUpButtonListener(){
        button_reg = (Button)findViewById(R.id.registration_button);
        button_reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            Profile profile = new Profile();
                            profile.setCompanyId(1);
                            profile.setDesignation("");
                            profile.setDepartment("");
                            User user = new User();
                            user.setFirstName(etFirstName.getText().toString());
                            user.setLastName(etLastName.getText().toString());
                            user.setEmail(etEmail.getText().toString());
                            user.setPassword(etPassword.getText().toString());
                            profile.setUser(user);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            String profileString = gson.toJson(profile);


                            JSONObject jsonProfileInfo  = new JSONObject();
                            //a default company id is set
                            jsonProfileInfo.put("companyId",1);
                            //jsonProfileInfo.put("department","");
                            //jsonProfileInfo.put("designation","");

                            JSONObject jsonUserInfo  = new JSONObject();
                            jsonUserInfo.put("firstName", etFirstName.getText().toString());
                            jsonUserInfo.put("lastName", etLastName.getText().toString());
                            jsonUserInfo.put("email", etEmail.getText().toString());
                            jsonUserInfo.put("password", etPassword.getText().toString());

                            jsonProfileInfo.put("user", jsonUserInfo);

                            org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
                            packetHeader.setAction(ACTION.SIGN_UP);
                            packetHeader.setRequestType(REQUEST_TYPE.AUTH);
                            packetHeader.setSessionId("");
                            new BackgroundWork().execute(packetHeader, profileString, new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    if(msg != null )
                                    {
                                        String stringSignInResponse = (String)msg.obj;
                                        if(stringSignInResponse != null)
                                        {
                                            Gson gson = new Gson();
                                            SignInResponse signInResponse = gson.fromJson(stringSignInResponse, SignInResponse.class);
                                            if(signInResponse.isSuccess())
                                            {
                                                //login and then goto complete profile page
                                                JSONObject user = new JSONObject();
                                                try
                                                {
                                                    user.put("userName", etEmail.getText().toString());
                                                    user.put("password", etPassword.getText().toString());
                                                }
                                                catch(Exception ex)
                                                {

                                                }
                                                String userString = user.toString();
                                                org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
                                                packetHeader.setAction(ACTION.SIGN_IN);
                                                packetHeader.setRequestType(REQUEST_TYPE.AUTH);
                                                //new BackgroundWork().execute(packetHeader, "{\"userName\": \"" + "bdlions@gmail.com" + "\", \"password\": \"" + "password" + "\"}", new IServerCallback() {
                                                new BackgroundWork().execute(packetHeader, userString, new Handler(){
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
                                                                    //session.createLoginSession(identity, signInResponse.getSessionId());
                                                                    session.createLoginSession(etEmail.getText().toString(), signInResponse.getSessionId());
                                                                    Intent login_intent = new Intent(SignUp.this, EditProfile.class);
                                                                    startActivity(login_intent);
                                                                }
                                                                else
                                                                {
                                                                    AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(SignUp.this);
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
                                            else
                                            {
                                                AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(SignUp.this);
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


                        //Intent sign_up_intent = new Intent(SignUp.this, CompleteProfileIndicator.class);
                        //startActivity(sign_up_intent);
                    }
                }
        );
    }
}
