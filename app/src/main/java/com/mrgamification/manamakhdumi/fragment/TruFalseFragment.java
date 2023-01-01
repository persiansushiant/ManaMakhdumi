package com.mrgamification.manamakhdumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.trufalsemodel;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Collections;

public class TruFalseFragment extends BaseFragment {
    TextView soal;
    Button correct,wrong;
    ArrayList<trufalsemodel> myArr = new ArrayList<>();


    public TruFalseFragment() {
    }

    public static TruFalseFragment newInstance() {
        TruFalseFragment fragment = new TruFalseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_truefalse, container, false);
        GetViews(v);
        FillData();
        Collections.shuffle(myArr);
        SetTextForMe();
        SetOnClickListeners();
        return v;
    }

    private void SetTextForMe() {
        soal.setText(myArr.get(0).getQuestion());
    }

    private void SetOnClickListeners() {
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myArr.get(0).isTf()==true){
                    RightAnswer();
                }else{
                    TryAgain();
                }
            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myArr.get(0).isTf()==false){
                    RightAnswer();

                }else{
                    TryAgain();

                }
            }
        });
    }

    private void RightAnswer() {
        new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").
                setContentText(myArr.get(0).getPayam()).setConfirmText("باشه").show();
        myArr.remove(0);
        if(myArr.size()==0){
            chooseItemFragment truFalseFragment = chooseItemFragment.newInstance();
            ((MainActivity)getActivity()).showFragment(truFalseFragment);
        }else{
            SetTextForMe();

        }
    }

    private void TryAgain() {
        new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE).setTitleText("اشتباه")
                .setContentText("اشتباه جواب داده اید").setConfirmText("باشه").show();
    }

    private void GetViews(View v) {
        soal = (TextView) v.findViewById(R.id.txt);
        wrong=(Button) v.findViewById(R.id.wrong);
        correct=(Button) v.findViewById(R.id.correct);
    }

    private void FillData() {
        myArr.add(new trufalsemodel("آیا افراد مبتلا به دیابت باید شکر، نان، برنج و سیب زمینی را از برنامه غذایی خود حذف کنند",false,"رژیم غذایی مناسب برای افراد مبتلا به دیابت یک رژیم غذایی متعادل است"));
        myArr.add(new trufalsemodel("میوه خوراکی سالمی است و حتی اگر زیاد استفاده شود مشکلی ندارد",false,"طبق هرم غذایی و الگوی غذای سالم هر فرد باید روزانه بین ۲تا۴واحد میوه مصرف کند.اما میوه ها حاوی کربوهیدرات هستند ، بنابراین می\u200Cتوانند روی قند خون تاثیر بگذارند و آن را افزایش دهند در نتیجه اگر بیش از حد استفاده شوند باعث افزایش قند خون و مدیریت سخت تر دیابت می\u200Cشوند."));
        myArr.add(new trufalsemodel("نیازی به تست قند ندارم و از روی علائم متوجه وضعیت قند خون خود می\u200Cشوم.",false,"بدن افراد ممکن است به قند خون بالا یا پایین عادت کند و دیگر علائمی نشان ندهد \n" +
                "وگاهی علائم افت قند خون و قند بالا می\u200Cتوانند شبیه به هم باشند و اگر فرد بخواهد صرفا از روی علائم وضعیت قند خون خود را تشخیص دهد، احتمال خطا در تشخیص بسیار زیاد خواهد بود.\n"));
        myArr.add(new trufalsemodel("افراد لاغر نیز ممکن است به دیابت مبتلا \u200Cشوند. ",true,"عامل چاقی یا اضافه وزن فقط یکی از عوامل خطر دیابت نوع 2 است اما تنها دلیل بروز آن نیست "));

    }
}
