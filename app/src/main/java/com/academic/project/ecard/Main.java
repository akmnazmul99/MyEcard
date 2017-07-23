package com.academic.project.ecard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    private static Button button_sign_up, button_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Adobe Devanagari.ttf");
        TextView myTextView = (TextView)findViewById(R.id.login_custom_text);
        myTextView.setTypeface(myTypeface);

        onClickSignUpButtonListener();
        onClickLoginButtonListener();
    }


    public void onClickSignUpButtonListener(){
        button_sign_up = (Button)findViewById(R.id.sign_up_button);
        button_sign_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sign_up_intent = new Intent(Main.this, SignUp.class);
                        startActivity(sign_up_intent);
                    }
                }
        );
    }

    public void onClickLoginButtonListener(){
        button_sign_in = (Button)findViewById(R.id.sign_in_button);
        button_sign_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sign_up_intent = new Intent(Main.this, Login.class);
                        startActivity(sign_up_intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
