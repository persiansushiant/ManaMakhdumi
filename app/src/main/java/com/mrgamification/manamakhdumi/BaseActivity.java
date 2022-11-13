package com.mrgamification.manamakhdumi;

import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mrgamification.manamakhdumi.activity.PopupActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;

import java.util.Date;


public class BaseActivity extends AppCompatActivity {

    Typeface lalezarFont;
    Dialog dialog;
    private SharedPreferences prefs;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;


    public String token = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        this.lalezarFont = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/lalezar.ttf");
    }

    public String GetSharedPrefrenceByKey(String key) {
        return getSharedPreferences().getString(key, key);
    }

    public boolean GetSharedPrefrenceByKeyBOolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public void SaveInSharedPref(String key, String result) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, result);
        editor.commit();
    }

    public void SaveInSharedPrefBoolean(String key, boolean result) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, result);
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        if (this.prefs == null) {
            this.prefs = getApplicationContext().getSharedPreferences("movakel", 0);
        }
        return this.prefs;
    }


    public void SendSMS(String smsMsg) {
        Log.wtf("sendSMS", smsMsg);
        SaveInSharedPref("smsMsg", smsMsg);
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pendingIntent = null;
        smsManager.sendTextMessage("09368247347", (String) null, GetSharedPrefrenceByKey("smsMsg"), pendingIntent, pendingIntent);
//        Crouton.makeText((Activity) this, (CharSequence) "به پزشک شما اطلاع داده شد.", Style.CONFIRM).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


    public void showPD() {

        dialog = new Dialog(BaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.sdm_loading);

        ImageView view = (ImageView) dialog.findViewById(R.id.img);


        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

//Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(700); //Put desired duration per anim cycle here, in milliseconds

//Start animation
        view.startAnimation(anim);

        ;

        dialog.show();

    }

    public void hidePD() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkUser();
    }

    public void checkUser() {

//        if (sharedPreferencesManager.getStringValue("user") != null
//                && !(sharedPreferencesManager.getStringValue("user").equals(""))
//                && !(sharedPreferencesManager.getStringValue("user").equals("null"))) {
//            user = new Gson().fromJson(sharedPreferencesManager.getStringValue("user"), User.class);
//        }

    }

//    public void showLoginDialog(Activity activity) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_login);
//
//        final TextView message = (TextView) dialog.findViewById(R.id.message);
//        final TextView btn_login = (TextView) dialog.findViewById(R.id.btn_login);
//        final TextView btn_cancel = (TextView) dialog.findViewById(R.id.btn_cancel);
//        final TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
//
//        dialog_title.setText("ورود");
//        message.setText("کاربر عزیز برای استفاده از امکانات برنامه و یا دسترسی به پروفایل باید وارد برنامه شوید!");
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginActivity.launch(activity, "");
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }

    public void setLalezarFont(TextView view) {
        view.setTypeface(lalezarFont);
    }

    public void exit() {
        this.finish();
    }

    public void setAlarmForMe(DaruItem daruItem) {
        Intent intent = new Intent(BaseActivity.this, AlarmReceiver.class);
        intent.putExtra("whatID", daruItem.getId() + "");
        intent.putExtra("status", "1");
        pendingIntent = PendingIntent.getBroadcast(BaseActivity.this,
                daruItem.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Integer.parseInt(daruItem.getNextStop()),
                1000 * 60 * delay, pendingIntent);
        }
    }





