package com.mrgamification.manamakhdumi.activity;

import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.DefferedMana;
import com.mrgamification.manamakhdumi.model.Note;
import com.mrgamification.manamakhdumi.model.faramushi;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.service.ForegroundService;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
//            DoManaWorker(PopupActivity.this,"کاربر وارد صفحه دیالوگ  شد","enterPopUp");

        } catch (Exception e) {
            new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText(id).setContentText(e.getMessage()).show();
        }
    }



    private void deleteNotificationBar() {
//        StopMyService(PopupActivity.this);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(PopupActivity.this);

        notificationManager.deleteNotificationChannel(GenerateCode(daru.getId().intValue(),status)  + "");
        notificationManager.cancel(GenerateCode(daru.getId().intValue(),status) );
//        StartMyService(PopupActivity.this);

    }

    private void SetHandler() {
//                        if (Build.VERSION.SDK_INT <30) {
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (status == 5) {
//                    cancelAlarmForMe(PopupActivity.this, daru);
//                    SendSMS("بیمار در مصرف داروی "
//                            + daru.getDaruName() + " خود غفلت کرده.");
//                }
//                r.stop();
//                finish();
//            }
//        }, 180000);
//    }
    }


    private void SetMusicPlayer() {
        try {
            Uri notification =Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.song);

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
                r.stop();
                daru.setStatus(1);
                int length = Integer.parseInt(daru.getDaruLenght());
                long time = Long.parseLong(daru.getNextStop()) + length * 60 * 60 * 1000;
                daru.setNextStop(time + "");
                DaruItem.saveInTx(daru);
                setAlarmForMe(PopupActivity.this, daru);
                CheckFOrPasmande();
                DoManaWorker(PopupActivity.this,"کاربر قرص رو زد خورده"+daru.getDaruName()+"بار "+(daru.getStatus()-1),"usedDrug");

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();
                CheckFOrPasmande();
                DoManaWorker(PopupActivity.this,"کاربر  زد یاداوری"+daru.getDaruName()+"بار "+(daru.getStatus()-1),"dismissed");

                if (status == 5) {
                    cancelAlarmForMe(PopupActivity.this, daru);
                    SendSMS("بیمار در مصرف داروی "
                            + daru.getDaruName() + " خود غفلت کرده.");
                    CreateFaramushiForLaterInformation(daru);

                    long nexttime = Long.parseLong(daru.getNextStop()) + Integer.parseInt(daru.getDaruLenght()) * 60 * 60 * 1000;
                    daru.setNextStop(nexttime + "");
                    DaruItem.saveInTx(daru);
                    setAlarmForMe(PopupActivity.this, daru);
                }


            }
        });
    }

    private void CheckFOrPasmande() {
        List<faramushi> myList = faramushi.find(faramushi.class, "daru_item = ?", daru.getId() + "");
        Log.wtf("tedad", myList.size() + "");
        if (myList.size() == 0) {
            finish();
        } else {
            for (faramushi faramushi : myList) {
                SweetAlertDialog mySweeti = new SweetAlertDialog(PopupActivity.this, SweetAlertDialog.WARNING_TYPE);
                mySweeti.setTitleText("داروی قبل خود را چه کردید؟");
                mySweeti.setContentText(getContentTextForFaramushi(faramushi, daru));
                mySweeti.setConfirmText("کردم");
                mySweeti.setCancelText("نکردم");
                mySweeti.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        DoManaWorker(PopupActivity.this,"کاربر قرص فراموشی رو زد نخورده"+daru.getDaruName(),"usedpastFrug");

                        faramushi.delete();
                        CheckTedadPasmande(mySweeti);
                        mySweeti.dismissWithAnimation();
                    }
                });
                mySweeti.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        DoManaWorker(PopupActivity.this,"کاربر قرص فراموشی رو زد خورده"+daru.getDaruName(),"usedpastFrug");

                        faramushi.delete();
                        CheckTedadPasmande(mySweeti);
                        mySweeti.dismissWithAnimation();



                    }
                });
                mySweeti.show();
            }

        }
    }

    private void CheckTedadPasmande(SweetAlertDialog sweetAlertDialog) {
        List<faramushi> myList = faramushi.find(faramushi.class, "daru_item = ?", daru.getId() + "");
        Log.wtf("tedad bade hazf", myList.size() + "");
        if (myList.size() == 0) {
            sweetAlertDialog.dismiss();
            finish();
        }
    }

    private String getContentTextForFaramushi(faramushi faramushi, DaruItem daru) {
        long ekhtelaf = System.currentTimeMillis() - Long.parseLong(faramushi.getTime());
        long hour = TimeUnit.MILLISECONDS.toHours(ekhtelaf);
        String g = "شما باید داروی" + daru.getDaruName() + "خود را " + hour + " ساعت " + "  پیش مصرف میکردید، نهایتا مصرف کردید؟";
        return g;
    }


    private void GetDaru() {
        id = getIntent().getStringExtra("whatID");
        Log.wtf("whatID id in popup", id);
        daru = DaruItem.findById(DaruItem.class, Long.parseLong(id));
        status = daru.getStatus();
    }

    private void GetViews() {
        img = (ImageView) findViewById(R.id.img);
        ok = (Button) findViewById(R.id.btnOne);
        cancel = (Button) findViewById(R.id.btnTwo);
        tv = (TextView) findViewById(R.id.tv);
    }

    private void SetGif() {
        switch (status + "") {
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
