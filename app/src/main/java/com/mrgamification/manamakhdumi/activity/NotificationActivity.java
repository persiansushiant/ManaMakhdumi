package com.mrgamification.manamakhdumi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.BaseActivity;
import com.mrgamification.manamakhdumi.R;

public class NotificationActivity extends BaseActivity {
    ImageView img;
    Button ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        this.setFinishOnTouchOutside(false);


        img = (ImageView) findViewById(R.id.img);
        ok = (Button) findViewById(R.id.btnOne);
        Glide.with(this).load(R.raw.happy).into(img);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
            }
        });

    }

}
