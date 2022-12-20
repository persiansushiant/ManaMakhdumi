package com.mrgamification.manamakhdumi.activity;


import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.model.Badge;
import com.mrgamification.manamakhdumi.service.ForegroundService;


public class SplashActivity extends BaseActivity {

//    private NotificationManager mManager;
//    public static final String ANDROID_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID";
//    public static final String IOS_CHANNEL_ID = "com.chikeandroid.tutsplustalerts.IOS";
//    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
//    public static final String IOS_CHANNEL_NAME = "IOS CHANNEL";
//    private NotificationUtils mNotificationUtils;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (!isForeGroundRunning())
            StartMyService(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (GetSharedPrefrenceByKey("usershenas").equals("usershenas")) {
                    AskMana();
                } else {


                    DoTheRest();

                }
            }
        }, 1500);

    }

    private void DoTheRest() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (GetSharedPrefrenceByKey("tut").equals("tut"))
                startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
            else
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        }
        finish();

    }

    private void AskMana() {

        Dialog dialog = new Dialog(SplashActivity.this);
        dialog.setContentView(R.layout.dialog_ask_mana);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        TextInputEditText userName = (TextInputEditText) dialog.findViewById(R.id.userName);
        Button btn = (Button) dialog.findViewById(R.id.btn);
        TextInputLayout t1 = (TextInputLayout) dialog.findViewById(R.id.t1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().length() == 0)
                    t1.setError("فیلد رو پر کن");
                else {
//                    new Badge( "firstpill", "firstpill", "pill", 1).save();
//                    new Badge("secondpill", "secondpill", "pill", 2).save();
//                    new Badge( "thirdpill", "thirdpill", "pill", 3).save();
//                    new Badge( "firstnote", "firstnote", "note", 1).save();
//                    new Badge( "secondnote", "secondnote", "note", 2).save();
//                    new Badge( "thirdnote", "thirdnote", "note", 3).save();
//                    new Badge( "firsttest", "firsttest", "quiz", 1).save();
//                    new Badge( "secondtest", "secondtest", "quiz", 2).save();
//                    new Badge( "thirdtest", "firsthirdtesttpill", "quiz", 3).save();
                    SaveInSharedPref("usershenas", userName.getText().toString());
                    DoTheRest();
                }
            }
        });


        dialog.show();
    }


    public boolean isForeGroundRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (ForegroundService.class.getName().equals(serviceInfo.service.getClassName())) {
                Log.w("service", "not running");
                return true;
            }
        }
        Log.w("service", "not running");
        return false;
    }


}