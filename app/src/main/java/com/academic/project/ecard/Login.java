package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private static Button button_login, button_forget_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onClickLogInButtonListener();
        onClickForgetPasswordButtonListener();
    }
    public void onClickLogInButtonListener(){
        button_login = (Button)findViewById(R.id.login_button);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent login_intent = new Intent(Login.this, AllCards.class);
                        startActivity(login_intent);
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
