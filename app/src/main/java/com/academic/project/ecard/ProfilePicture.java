package com.academic.project.ecard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ProfilePicture extends AppCompatActivity {
    private static Button button_image_download;
    private static ImageView profile_picture_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_image_download = (Button) findViewById(R.id.b_image_download);
        profile_picture_image_view = (ImageView) findViewById(R.id.iv_image);
        button_image_download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent image_download = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                image_download.putExtra("crop", "true");
                image_download.putExtra("aspectX", 1);
                image_download.putExtra("aspectY", 1);
                image_download.putExtra("outputX", 200);
                image_download.putExtra("outputY", 200);
                image_download.putExtra("return-data", true);
                startActivityForResult(image_download, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap_image = extras.getParcelable("data");
            profile_picture_image_view.setImageBitmap(bitmap_image);

        }

    }
}
