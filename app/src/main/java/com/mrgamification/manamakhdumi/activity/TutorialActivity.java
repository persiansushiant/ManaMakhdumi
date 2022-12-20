package com.mrgamification.manamakhdumi.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.mrgamification.manamakhdumi.R;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class TutorialActivity extends BaseActivity {
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_activity_layout);
        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
//                String s=GetSharedPrefrenceByKey("tcount");
//                int count=0;
//                if(s.equals("tcount"))
//                    count=0;
//                else
//                    count=Integer.parseInt("tcount");
//                if(count==4)
//                SaveInSharedPref("Tutorial","y");
//                else {
//                    count++;
                    SaveInSharedPref("tut","asd");
//                }
            startActivity(new Intent(TutorialActivity.this, MainActivity.class));
            finish();
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("بازی", "با انجام بازی دانش خود را بالا ببرید",
                Color.parseColor("#678FB4"), R.drawable.gamee, R.drawable.onee);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("یادآوری", "داروهای خود را وارد اپلیکیشن کنید تا مصرفشان به تعویق نیفتد",
                Color.parseColor("#65B0B4"), R.drawable.medicine, R.drawable.twoo);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("آواتار", "مراقب حال آواتار خود باشید.",
                Color.parseColor("#9B90BC"), R.drawable.fish, R.drawable.threee);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}