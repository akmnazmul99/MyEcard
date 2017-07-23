package com.academic.project.ecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CompleteProfileIndicator extends AppCompatActivity {
    private static RoundedImageView rounded_image_view;
    private static Button button_complete_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile_indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickCompleteProfileRoundedImageViewListener();
        onClickCompleteProfileButtonListener();

    }
    public void onClickCompleteProfileRoundedImageViewListener(){
        rounded_image_view = (RoundedImageView)findViewById(R.id.contact_imageView_round_1);
        rounded_image_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profile_picture_intent = new Intent(CompleteProfileIndicator.this, ProfilePicture.class);
                        startActivity(profile_picture_intent);
                    }
                }
        );
    }
    public void onClickCompleteProfileButtonListener(){
        button_complete_profile = (Button)findViewById(R.id.complete_profile_button);
        button_complete_profile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent complete_profile_intent = new Intent(CompleteProfileIndicator.this, CompleteProfile.class);
                        startActivity(complete_profile_intent);
                    }
                }
        );
    }
}
