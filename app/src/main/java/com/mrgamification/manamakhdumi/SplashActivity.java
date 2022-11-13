package com.mrgamification.manamakhdumi;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mrgamification.manamakhdumi.activity.PopupActivity;
import com.mrgamification.manamakhdumi.activity.TutorialActivity;
import com.mrgamification.manamakhdumi.reciever.MyNotificationReciever;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;


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
      new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    if(GetSharedPrefrenceByKey("tut").equals("tut"))
                    startActivity(new Intent(SplashActivity.this, TutorialActivity.class));
                    else
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }

            }
        }, 1500);

    }


    private void FirstNotif() {
        if(GetSharedPrefrenceByKey("FirstNotif").equals("FirstNotif")) {
            Intent fullScreenIntent = new Intent(this, MainActivity.class);
            fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                    fullScreenIntent, PendingIntent.FLAG_IMMUTABLE);
            createNotificationChannel();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "14")
                    .setSmallIcon(R.drawable.error_center_x)
                    .setContentTitle("خوش آمدید")
                    .setContentText("به اپلیکیشن کووینو خوش آمدید.")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("به اپلیکیشن کوینو که یادآور دارو هست خوش آمدید."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    .setContentIntent(fullScreenPendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(1414, builder.build());
            SaveInSharedPref("FirstNotif","s");
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("14", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




}