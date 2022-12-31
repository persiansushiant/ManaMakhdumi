package com.mrgamification.manamakhdumi.activity;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.fragment.AvatarFragment;
import com.mrgamification.manamakhdumi.fragment.BadgeFragment;
import com.mrgamification.manamakhdumi.fragment.DaruFragment;
import com.mrgamification.manamakhdumi.fragment.FatemeFragment;
import com.mrgamification.manamakhdumi.fragment.GameFragment;
import com.mrgamification.manamakhdumi.fragment.NoteFragment;
import com.mrgamification.manamakhdumi.model.DefferedMana;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    DaruFragment daruFragment;
    GameFragment gameFragment;
    AvatarFragment avatarFragment;
    NoteFragment noteFragment;
    FatemeFragment fatemeFragment;
//    BadgeFragment badgeFragment;
    Boolean isFirstPage = true;
    Boolean isExitable = false;
    ImageView img_daru;
    ImageView img_note;
    ImageView img_avatar;
    ImageView img_game;
//    ImageView img_badge;
    LinearLayout btn_daru;
    LinearLayout btn_avatar;
    LinearLayout btn_game;
//    LinearLayout btn_badge;
    LinearLayout btn_exit;
    LinearLayout btn_note;
//    String[] permissions = {"android.permission.SCHEDULE_EXACT_ALARM","android.permission.SEND_SMS"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitiateFragments();
        showFragment(avatarFragment);
        getViews();
        SetOnCLickListeners();
        showCaseView();
//        checkandroi12Permission();
//        checkPermissions();
        ifHuaweiAlert();
        AntiDoze();
//        DoManaWorker(MainActivity.this,"کاربر وارد اپ شد","enterMain");
        Log.wtf("manauser",GetSharedPrefrenceByKey("usershenas"));

    }



    private void AntiDoze() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1) {
            String pkg=getPackageName();
            PowerManager pm=getSystemService(PowerManager.class);
            if (!pm.isIgnoringBatteryOptimizations(pkg)) {
                Intent i=
                        new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                                .setData(Uri.parse("package:"+pkg));
                startActivity(i);
            }
        }
    }
    private void checkandroi12Permission() {
        if (Build.VERSION.SDK_INT > 32) {
            AlarmManager am= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            boolean g=am.canScheduleExactAlarms();
            Log.wtf("am i allowed",g+"");
            if(!g)
                startActivity( new Intent("Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM"));
        }
    }
//    public void onRequestPermissionsResult(int requestCode, String[] permissions2, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions2, grantResults);
//        if (requestCode != 10) {
//            return;
//        }
//        if (grantResults.length <= 0 || grantResults[0] != 0) {
//            SaveInSharedPref("permisions","n");
//
//            Log.wtf("permissions", "not granted");
//        } else {
//            SaveInSharedPref("permisions","y");
//            Log.wtf("permission", "granted");
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.S)
//    private boolean checkPermissions() {
//
//        if(!GetSharedPrefrenceByKey("permisions").equals("y")) {
//            List<String> listPermissionsNeeded = new ArrayList<>();
//            for (String p : this.permissions) {
//                if (ContextCompat.checkSelfPermission(this, p) != 0) {
//                    listPermissionsNeeded.add(p);
//                }
//            }
//            if (listPermissionsNeeded.isEmpty()) {
//                return true;
//            }
//            ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10);
//            return false;
//        }
//        return true;
//    }
    private void showCaseView() {

    }


    private void SetOnCLickListeners() {
        btn_avatar.setOnClickListener(this);
        btn_daru.setOnClickListener(this);
        btn_game.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_note.setOnClickListener(this);
//        btn_badge.setOnClickListener(this);
    }

    private void InitiateFragments() {
        daruFragment = DaruFragment.newInstance();
        gameFragment = GameFragment.newInstance();
        avatarFragment = AvatarFragment.newInstance();
        noteFragment= NoteFragment.newInstance();
//        badgeFragment=BadgeFragment.newInstance();
        fatemeFragment=FatemeFragment.newInstance();
    }

    private void getViews() {
        btn_daru = (LinearLayout) findViewById(R.id.btn_daru);
        btn_game = (LinearLayout) findViewById(R.id.btn_game);
        btn_note = (LinearLayout) findViewById(R.id.btn_note);
        btn_avatar = (LinearLayout) findViewById(R.id.btn_avatar);
        img_daru = (ImageView) findViewById(R.id.img_daru);
        img_avatar = (ImageView) findViewById(R.id.img_avatar);
        img_game = (ImageView) findViewById(R.id.img_game);
        img_note = (ImageView) findViewById(R.id.img_note);
        btn_exit = (LinearLayout) findViewById(R.id.btn_exit);
//        img_badge=(ImageView) findViewById(R.id.img_badge);
//        btn_badge=(LinearLayout) findViewById(R.id.btn_badge);
    }

    public void showFragment(Fragment fragment) {
        if (fragment == avatarFragment) {
            isFirstPage = true;
        } else {
            isFirstPage = false;
        }
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (isFirstPage) {
            if (isExitable) {
                super.onBackPressed();
            } else {
                showToast("برای خروج دوباره کلیک کنید", 1);
                isExitable = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExitable = false;
                    }
                }, 4000);
            }
        } else {
            resetIcons();
            Glide.with(this).load(R.drawable.washs).transition(withCrossFade())
                    .into(img_avatar);
            showFragment(avatarFragment);
        }
    }

    public void showToast(String message, int faz) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();



    }

    private void resetIcons() {
        Glide.with(this).load(R.drawable.notifs).transition(withCrossFade()).into(img_daru);
        Glide.with(this).load(R.drawable.contacts).transition(withCrossFade()).into(img_game);
        Glide.with(this).load(R.drawable.washs).transition(withCrossFade()).into(img_avatar);
        Glide.with(this).load(R.drawable.note).transition(withCrossFade()).into(img_note);
//        Glide.with(this).load(R.drawable.trophy).transition(withCrossFade()).into(img_badge);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_exit:
                onBackPressed();
                break;

            case R.id.btn_avatar: {
//                resetIcons();
                Glide.with(this).load(R.drawable.washs).transition(withCrossFade()).into(img_avatar);
                showFragment(avatarFragment);
            }
            break;
            case R.id.btn_game: {
//                resetIcons();
                Glide.with(this).load(R.drawable.contacts).transition(withCrossFade()).into(img_game);
                showFragment(fatemeFragment);
            }
            break;
            case R.id.btn_daru: {
//                resetIcons();
                Glide.with(this).load(R.drawable.notifs).transition(withCrossFade()).into(img_daru);
                showFragment(daruFragment);
            }
            break;
            case R.id.btn_note: {
//                resetIcons();
                Glide.with(this).load(R.drawable.note).transition(withCrossFade()).into(img_note);
                showFragment(noteFragment);
            }
//            case R.id.btn_badge: {
////                resetIcons();
////                Glide.with(this).load(R.drawable.trophy).transition(withCrossFade()).into(img_badge);
////                showFragment(badgeFragment);
//            }
//            break;

        }
    }
    private void ifHuaweiAlert() {
        final SharedPreferences settings = getSharedPreferences("ProtectedApps", MODE_PRIVATE);
        final String saveIfSkip = "skipProtectedAppsMessage";
        boolean skipMessage = settings.getBoolean(saveIfSkip, false);
        if (!skipMessage) {
            final SharedPreferences.Editor editor = settings.edit();
            Intent intent = new Intent();
            intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            if (isCallable(intent)) {
                final AppCompatCheckBox dontShowAgain = new AppCompatCheckBox(this);
                dontShowAgain.setText("Do not show again");
                dontShowAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean(saveIfSkip, isChecked);
                        editor.apply();
                    }
                });

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Huawei Protected Apps")
                        .setMessage(String.format("%s requires to be enabled in 'Protected Apps' to function properly.%n", getString(R.string.app_name)))
                        .setView(dontShowAgain)
                        .setPositiveButton("Protected Apps", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                huaweiProtectedApps();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            } else {
                editor.putBoolean(saveIfSkip, true);
                editor.apply();
            }
        }
    }

    private boolean isCallable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void huaweiProtectedApps() {
        try {
            String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial();
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ignored) {
        }
    }

    private String getUserSerial() {
        //noinspection ResourceType
        Object userManager = getSystemService("user");
        if (null == userManager) return "";

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod("myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(android.os.Process.class, (Object[]) null);
            Method getSerialNumberForUser = userManager.getClass().getMethod("getSerialNumberForUser", myUserHandle.getClass());
            Long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
            if (userSerial != null) {
                return String.valueOf(userSerial);
            } else {
                return "";
            }
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return "";
    }
}