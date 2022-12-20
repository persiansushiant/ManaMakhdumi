package com.mrgamification.manamakhdumi.reciever;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mrgamification.manamakhdumi.activity.NotificationActivity;

public class MyNotificationReciever  extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    public void showNotification(Context context) {
        Log.wtf("in reciever","yes");


        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);

//        Toast.makeText(context, "لطفا داروی خود را استعمال کنید.", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();
//        new SweetAlertDialog(context).setTitleText("یادآور دارو").setContentText("لطفا داروی خود را اتعمال نمایید مصرف به موقع دارو حیاتی است").
//                setConfirmText("استفاده کردم").setCancelText("باشه بعدا").show();
        Intent intent1 = new Intent(context, NotificationActivity.class);
        intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}