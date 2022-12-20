package com.mrgamification.manamakhdumi.activity;

import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkRequest;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.api.APIClient;
import com.mrgamification.manamakhdumi.api.APIInterface;
import com.mrgamification.manamakhdumi.model.Badge;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.DefferedMana;
import com.mrgamification.manamakhdumi.model.faramushi;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.service.ForegroundService;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity {

    Typeface lalezarFont;
    Dialog dialog;
    private SharedPreferences prefs;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    WorkRequest myWorkRequest;
    APIInterface apiInterface;


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

    public static void CreateFaramushiForLaterInformation(DaruItem daru) {
        faramushi faramushi = new faramushi(daru.getId().intValue(), Long.parseLong(daru.getNextStop()) + "");
        faramushi.save();
    }

    public static void SendSMS(String smsMsg) {
        Log.wtf("sendSMS", smsMsg);
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pendingIntent = null;
        smsManager.sendTextMessage("09368247347", (String) null, smsMsg, pendingIntent, pendingIntent);
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

    public void StartMyService(Context myContext) {
        Log.wtf("service", "started");
        Intent serviceIntent = new Intent(myContext, ForegroundService.class);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R)
            startForegroundService(serviceIntent);
        else {
            startService(serviceIntent);
        }
    }

    public void StopMyService(Context myContext) {
        Intent myService = new Intent(myContext, ForegroundService.class);
        stopService(myService);
    }


    public void setLalezarFont(TextView view) {
        view.setTypeface(lalezarFont);
    }

    public void exit() {
        this.finish();
    }

    public static void setAlarmForMe(Context mContext, DaruItem daruItem) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra(mContext.getString(R.string.mykey), daruItem.getId() + "");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                daruItem.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Long.parseLong(daruItem.getNextStop()),
                1000 * 60 * delay, pendingIntent);
        SetManaAction(mContext, "addDrug", "alarm for "+daruItem.getDaruName() + " added at" + PrettifyMyTIme(daruItem.getNextStop()) );


    }

    public static String PrettifyMyTIme(String nextStop) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(Long.parseLong(nextStop));
        int hour = calender.get(Calendar.HOUR);
        int minute = calender.get(Calendar.MINUTE);
        return " " + hour + ":" + minute + " ";
    }

    public static String getManaUser(Context mContext) {
        String ManaUser = mContext.getApplicationContext().getSharedPreferences("movakel", 0).getString("manaUser", "manaUser");
        return ManaUser;
    }

    public static void SetManaAction(Context mContext, String userAction, String tozih) {
        String ManaUser = mContext.getApplicationContext().getSharedPreferences("movakel", 0).getString("manaUser", "manaUser");

        DefferedMana mana = new DefferedMana("mana", ManaUser, tozih, userAction, PrettifyMyTIme(System.currentTimeMillis() + ""));
        mana.save();
//        DoManaWorker(mContext,ManaUser);
    }

    public static void cancelAlarmForMe(Context mCOntext, DaruItem daruItem) {
        AlarmManager alarmManager = (AlarmManager) mCOntext.getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(mCOntext, AlarmReceiver.class);

        intent.putExtra("whatID", daruItem.getId() + "");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mCOntext, daruItem.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }

    public static int GenerateCode(int id, int status) {
        return status * 100 + id;
    }

    public static void AddUp(Context mContext, String action) {
        String pilltedad = mContext.getApplicationContext().getSharedPreferences("movakel", 0).getString(action, "0");
        int realtedad = Integer.parseInt(pilltedad);
        mContext.getApplicationContext().getSharedPreferences("movakel", 0).edit().putString(action, (realtedad + 1) + "");

    }

    public void DoManaWorker(Context context, String myAction, String tozih) {
        String manaUser = context.getApplicationContext().getSharedPreferences("movakel", 0).getString("usershenas", "test");
        DefferedMana mana = new DefferedMana("mana", manaUser, tozih, myAction, PrettifyMyTIme(System.currentTimeMillis()+""));
        mana.save();
        List<DefferedMana> allMana = DefferedMana.listAll(DefferedMana.class);

//        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .build();
//
//        myWorkRequest = new OneTimeWorkRequest.Builder(ManaWorker.class)
//                .addTag("soli" + System.currentTimeMillis())
//                .setBackoffCriteria(BackoffPolicy.LINEAR,
//                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
//                        TimeUnit.SECONDS).setConstraints(constraints)
//                .build();
//        WorkManager.getInstance(context).enqueue(myWorkRequest);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Log.wtf("manauser", manaUser);
//        Toast.makeText(context, "txts is"+manaUser, Toast.LENGTH_LONG).show();

        for (DefferedMana manajoon : allMana) {


            Call<String> call = apiInterface.MakeManaCum("mana", manaUser, manajoon.getTozih(),
                    manajoon.getAction(), manajoon.getTime(), manajoon.getId() + "");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.equals("Yes")) {
                        DefferedMana book = DefferedMana.findById(DefferedMana.class, mana.getId());
                        book.delete();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}





