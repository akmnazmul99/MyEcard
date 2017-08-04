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

import com.auction.dto.User;
import com.auction.dto.response.SignInResponse;
import com.auction.util.ACTION;
import com.auction.util.REQUEST_TYPE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.auction.udp.BackgroundWork;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    private static Button button_login, button_forget_password;
    private static EditText etIdentity, etPassword;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());
        etIdentity = (EditText) findViewById(R.id.et_login_email);
        etPassword = (EditText) findViewById(R.id.et_login_password);

        onClickLogInButtonListener();
        onClickForgetPasswordButtonListener();
    }
    public void onClickLogInButtonListener(){
        button_login = (Button)findViewById(R.id.login_button);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject user = new JSONObject();
                        try
                        {
                            user.put("userName", etIdentity.getText().toString());
                            user.put("password", etPassword.getText().toString());
                        }
                        catch(Exception ex)
                        {

                        }

                        //User user = new User();
                        //user.setUserName(identity);
                        //user.setPassword(password);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        //String userString = gson.toJson(user);
                        String userString = user.toString();
                        org.bdlions.transport.packet.PacketHeaderImpl packetHeader = new org.bdlions.transport.packet.PacketHeaderImpl();
                        packetHeader.setAction(ACTION.SIGN_IN);
                        packetHeader.setRequestType(REQUEST_TYPE.AUTH);
                        //new BackgroundWork().execute(packetHeader, "{\"userName\": \"" + "bdlions@gmail.com" + "\", \"password\": \"" + "password" + "\"}", new IServerCallback() {
                        new BackgroundWork().execute(packetHeader, userString, new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
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
                                        session.createLoginSession(etIdentity.getText().toString(), signInResponse.getSessionId());
                                        Intent login_intent = new Intent(Login.this, AllCards.class);
                                        startActivity(login_intent);

                                        //Intent login_intent = new Intent(getBaseContext(), MemberDashboard.class);
                                        //login_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        //startActivity(login_intent);
                                    }
                                    else
                                    {
                                        AlertDialog.Builder  sign_in_builder = new AlertDialog.Builder(Login.this);
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

                            }
                        });
                        //Intent login_intent = new Intent(Login.this, AllCards.class);
                        //startActivity(login_intent);
                    }
                }
        );
    }
    public void onClickForgetPasswordButtonListener(){
        button_forget_password = (Button)findViewById(R.id.login_forget_password_button);
        button_forget_password.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent login_intent = new Intent(Login.this, ForgetPassword.class);
                        startActivity(login_intent);
                    }
                }
        );
    }
//    public void onCheckboxClicked(View view) {
        // Is the view now checked?
       // boolean checked = ((CheckBox) view).isChecked();

//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkbox_meat:
//                if (checked)
//                // Put some meat on the sandwich
//                else
//                // Remove the meat
//                break;
//            case R.id.checkbox_cheese:
//                if (checked)
//                // Cheese me
//                else
//                // I'm lactose intolerant
//                break;
//            // TODO: Veggie sandwich
//        }
//    }

}
