package com.mrgamification.manamakhdumi.reciever;

import static android.content.Context.ALARM_SERVICE;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.CreateFaramushiForLaterInformation;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.GenerateCode;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.SendSMS;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.cancelAlarmForMe;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.setAlarmForMe;
import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.PopupActivity;
import com.mrgamification.manamakhdumi.helper.WakeLocker;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.faramushi;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

public class AlarmReceiver extends BroadcastReceiver {
    long whatID;
    private SharedPreferences prefs;
    Context myContext;
    DaruItem myDaru;
    boolean contin = true;
    int status = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        if (context == null)
            return;

        Log.wtf("alarmReciever", "been here hny");
        Initialize(context, intent);
        Toast.makeText(context, "we are here", Toast.LENGTH_SHORT).show();

        showNotif(context);
        WakeLocker.release();
    }

    private void showNotif(Context context) {
        try {
            DaruItem daru = DaruItem.findById(DaruItem.class, whatID);
            GainStatus(daru);

            if (contin) {
//                if (Build.VERSION.SDK_INT >30) {
//                    Intent i = new Intent(myContext, PopupActivity.class);
//                    i.putExtra("whatID", whatID + "");
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    myContext.startActivity(i);
//                } else {
                Intent fullScreenIntent = new Intent(myContext, PopupActivity.class);
                fullScreenIntent.putExtra("whatID", whatID + "");
                fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(myContext, myDaru.getId().intValue(),
                        fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    createNotificationChannel(myContext, new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build(),
                            Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.song));

                }
                String text = "لطفا داروی " + daru.getDaruName() + " خود را هرچه زودتر مصرف کنید.";
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//                builder.setSound(alarmSound);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, GenerateCode((int)whatID,status) + "")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("یادآور دارو")
                        .setContentText(text)
                        .setLights(Color.RED, 500, 500)
                        .setVibrate(pattern)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("مصرف دارو برای شما بسیار مهم میباشد سریع آن را مصرف کنید."))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setSound(alarmSound)
                        .setContentIntent(fullScreenPendingIntent)
                        .setTimeoutAfter(540000)
                        .setAutoCancel(false);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(myContext);
                notificationManager.notify(GenerateCode(daru.getId().intValue(),status), builder.build());
            }
        } catch (Exception e) {
            new SweetAlertDialog(myContext).setTitleText("عکس بده").setContentText(e.getMessage()).
                    setConfirmText("استفاده کردم").setCancelText("باشه بعدا").show();
        }
    }


    private void GainStatus(DaruItem daru) {
        long nextStop = Long.parseLong(daru.getNextStop());
        long vaghfe = System.currentTimeMillis() - nextStop;
        Log.wtf("vaghfe", (vaghfe / 60000) + ":" + (vaghfe % 60000) + " s");

        if (vaghfe < FaseleZamani(1)) {
            status = 2;

        } else if (vaghfe >= FaseleZamani(1) && vaghfe < FaseleZamani(2)) {
            status = 3;
        } else if (vaghfe >= FaseleZamani(2) && vaghfe < FaseleZamani(3)) {
            status = 4;

        } else if (vaghfe >= FaseleZamani(3) && vaghfe < FaseleZamani(4)) {
            status = 5;

        } else {
            status = 5;

            cancelAlarmForMe(myContext, daru);

            SendSMS("بیمار در مصرف داروی " + daru.getDaruName() + " خود غفلت کرده است.");
            CreateFaramushiForLaterInformation(daru);
            long nexttime = Long.parseLong(daru.getNextStop()) + Integer.parseInt(daru.getDaruLenght()) * 1000 * 60 * 60;
            daru.setNextStop(nexttime + "");
            setAlarmForMe(myContext, daru);
            contin = false;
        }
        daru.setStatus(status);

        DaruItem.saveInTx(daru);

    }


    private int FaseleZamani(int round) {
        return round * 60000 * delay;
    }

    private void Initialize(Context context, Intent intent) {
        myContext = context;
        whatID = Long.parseLong(intent.getStringExtra("whatID"));
        myDaru = DaruItem.findById(DaruItem.class, whatID);
        Log.wtf("whatID", whatID + "");

    }


    private void createNotificationChannel(Context mCOntext, AudioAttributes audioAttributes, Uri soundUri) {
        if (Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "کوینو";
            String description = mCOntext.getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(GenerateCode((int)whatID,status) +"", name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            channel.setSound(soundUri, audioAttributes);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            ((NotificationManager) mCOntext.getSystemService(NotificationManager.class)).createNotificationChannel(channel);
        }
    }


    private void vibrate(Context mCOntext) {
        Vibrator vibrator = (Vibrator) mCOntext.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(200);
        }
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
            this.prefs = myContext.getSharedPreferences("movakel", 0);
        }
        return this.prefs;
    }

    public String GetSharedPrefrenceByKey(String key) {
        return getSharedPreferences().getString(key, key);
    }
}