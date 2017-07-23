package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity {
    private static Button button_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        onClickSignUpButtonListener();
    }
    public void onClickSignUpButtonListener(){
        button_reg = (Button)findViewById(R.id.registration_button);
        button_reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sign_up_intent = new Intent(SignUp.this, CompleteProfileIndicator.class);
                        startActivity(sign_up_intent);
                    }
                }
        );
    }
}
