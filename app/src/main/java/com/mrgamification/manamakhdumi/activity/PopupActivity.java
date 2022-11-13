package com.mrgamification.manamakhdumi.activity;

import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.BaseActivity;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.application.MyApplication;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.faramushi;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

public class PopupActivity extends BaseActivity {
    ImageView img;
    Button ok, cancel;
    TextView tv;
    AlarmManager alarmManager;
    DaruItem daru;
    String id;
    int status;
    Ringtone r;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_theme);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            this.setFinishOnTouchOutside(false);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            GetViews();
            GetDaru();
            SetGif();
            SetTextForTV();
            SetClickListeners();
            SetMusicPlayer();
            SetHandler();
            deleteNotificationBar();

        } catch (Exception e) {
            new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText(id).setContentText(e.getMessage()).show();
        }
    }
    private void deleteNotificationBar() {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(PopupActivity.this);

        notificationManager.deleteNotificationChannel(daru.getId() + "");
        notificationManager.cancel(daru.getId().intValue());

    }
    private void SetHandler() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after delay
                if (status==5) {
//                    daru.setStatus(5);
//                    DaruItem.saveInTx(daru);
                    deleteAlarm();

                    SendSmSToMana();


                }
                r.stop();
                finish();
            }
        }, 180000);
    }


    private void SetMusicPlayer() {
        try {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();


        } catch (Exception e) {
            Log.wtf("problem", e.getMessage());

        }

    }

    private void SetTextForTV() {
        tv.setText("لطفا داروی " + daru.getDaruName() + " خود را هرچه زودتر مصرف کنید.");
    }

    private void SetClickListeners() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteOldNotification();
                r.stop();
                daru.setStatus(1);
                int length = Integer.parseInt(daru.getDaruLenght());
                long time = Long.parseLong(daru.getNextStop()) + length *60* 60*1000;
                daru.setNextStop(time + "");
                DaruItem.saveInTx(daru);
                setAlarmForMe(daru);


                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int negStatus = Integer.parseInt(status);
//                negStatus++;
//                daru.setStatus(negStatus);
////                daru.setStatus(daru.getStatus() + 1);
////                long time = System.currentTimeMillis() + 1000 * 60 * 10;
////                daru.setNextStop(time + "");
////
//                DaruItem.saveInTx(daru);
                if (status==5) {
//                    daru.setStatus(5);
//                    DaruItem.saveInTx(daru);

                    deleteAlarm();

                    SendSmSToMana();
                    faramushi faramushi=new faramushi(daru.getId().intValue(),Long.parseLong(daru.getNextStop())+"");
                    faramushi.save();
                    PendingIntent pendingIntent;
                    long nexttime=Long.parseLong(daru.getNextStop())+Integer.parseInt(daru.getDaruLenght())*60*60*1000;
                    daru.setNextStop(nexttime +"");
                    DaruItem.saveInTx(daru);
                    Intent intentz = new Intent(PopupActivity.this, AlarmReceiver.class);
                    intentz.putExtra("whatID", daru.getId() + "");
                    intentz.putExtra("status", "1");
                    pendingIntent = PendingIntent.getBroadcast(PopupActivity.this,
                            daru.getId().intValue(), intentz, PendingIntent.FLAG_IMMUTABLE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Integer.parseInt(daru.getNextStop()),
                            1000 * 60 * delay, pendingIntent);
                }
//                deleteOldNotification();
                r.stop();
                finish();
            }

        });
    }

    private void deleteAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(PopupActivity.this, AlarmReceiver.class);
        intent.putExtra("whatID", daru.getId() + "");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PopupActivity.this,
                daru.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
    }

    private void SendSmSToMana() {
        SendSMS("بیمار در مصرف داروی " + daru.getDaruName() + " خود غفلت کرده.");
    }

    private void GetDaru() {
        id = getIntent().getStringExtra("whatID");
        Log.wtf("whatID id in popup", id);

        daru = DaruItem.findById(DaruItem.class, Long.parseLong(id));
        status=daru.getStatus();
    }

    private void GetViews() {
        img = (ImageView) findViewById(R.id.img);
        ok = (Button) findViewById(R.id.btnOne);
        cancel = (Button) findViewById(R.id.btnTwo);
        tv = (TextView) findViewById(R.id.tv);
    }

    private void deleteOldNotification() {
            Intent intent = new Intent(PopupActivity.this, AlarmReceiver.class);

            intent.putExtra("whatID", daru.getId() + "");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(PopupActivity.this, daru.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);





    }



    private void SetGif() {
        switch (status+"") {
            case "2":
                Glide.with(this).load(R.raw.happy).into(img);

                break;


            case "3":
                Glide.with(this).load(R.raw.sad).into(img);

                break;

            case "4":
                Glide.with(this).load(R.raw.angry).into(img);

                break;

            case "5":
                Glide.with(this).load(R.raw.anxiety).into(img);

                break;
            default:
                Glide.with(this).load(R.raw.anxiety).into(img);

                break;
        }


    }



}
