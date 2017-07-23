package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EditProfile extends AppCompatActivity {
    private static Button button_select_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickSelectCardButtonListener();
    }
    public void onClickSelectCardButtonListener(){
        button_select_card = (Button)findViewById(R.id.select_card_button);
        button_select_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent select_card_intent = new Intent(EditProfile.this, UserProfile.class);
                        startActivity(select_card_intent);
                    }
                }
        );
    }
}
