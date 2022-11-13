package com.mrgamification.manamakhdumi;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.fragment.AvatarFragment;
import com.mrgamification.manamakhdumi.fragment.DaruFragment;
import com.mrgamification.manamakhdumi.fragment.GameFragment;
import com.mrgamification.manamakhdumi.model.DaruItem;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity implements View.OnClickListener {
    DaruFragment daruFragment;
    GameFragment gameFragment;
    AvatarFragment avatarFragment;
    Boolean isFirstPage = true;
    Boolean isExitable = false;
    ImageView img_daru;
    ImageView img_avatar;
    ImageView img_game;
    LinearLayout btn_daru;
    LinearLayout btn_avatar;
    LinearLayout btn_game;
    LinearLayout btn_exit;
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
    }

    private void InitiateFragments() {
        daruFragment = DaruFragment.newInstance();
        gameFragment = GameFragment.newInstance();
        avatarFragment = AvatarFragment.newInstance();
    }

    private void getViews() {
        btn_daru = (LinearLayout) findViewById(R.id.btn_daru);
        btn_game = (LinearLayout) findViewById(R.id.btn_game);
        btn_avatar = (LinearLayout) findViewById(R.id.btn_avatar);
        img_daru = (ImageView) findViewById(R.id.img_daru);
        img_avatar = (ImageView) findViewById(R.id.img_avatar);
        img_game = (ImageView) findViewById(R.id.img_game);
        btn_exit = (LinearLayout) findViewById(R.id.btn_exit);
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
                }, 2000);
            }
        } else {
            resetIcons();
            Glide.with(this).load(R.drawable.washs).transition(withCrossFade())
                    .into(img_avatar);
            showFragment(avatarFragment);
        }
    }

    public void showToast(String message, int faz) {
//        if (faz == 0)//warning
//            Crouton.makeText(MainActivity.this, message, Style.ALERT).show();
//        else
//            Crouton.makeText(MainActivity.this, message, Style.CONFIRM).show();


    }

    private void resetIcons() {
        Glide.with(this).load(R.drawable.notifs).transition(withCrossFade()).into(img_daru);
        Glide.with(this).load(R.drawable.contacts).transition(withCrossFade()).into(img_game);
        Glide.with(this).load(R.drawable.washs).transition(withCrossFade()).into(img_avatar);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_exit:
                onBackPressed();
                break;

            case R.id.btn_avatar: {
                resetIcons();
                Glide.with(this).load(R.drawable.washs).transition(withCrossFade()).into(img_avatar);
                showFragment(avatarFragment);
            }
            break;
            case R.id.btn_game: {
                resetIcons();
                Glide.with(this).load(R.drawable.contacts).transition(withCrossFade()).into(img_game);
                showFragment(gameFragment);
            }
            break;
            case R.id.btn_daru: {
                resetIcons();
                Glide.with(this).load(R.drawable.notifs).transition(withCrossFade()).into(img_daru);
                showFragment(daruFragment);
            }
            break;

        }
    }
}