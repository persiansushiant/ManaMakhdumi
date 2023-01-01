package com.mrgamification.manamakhdumi.fragment;

import static com.mrgamification.manamakhdumi.activity.BaseActivity.AddUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.Question;
import com.mrgamification.manamakhdumi.model.fatemeQuestion;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Collections;

public class FatemeQuestionFragment extends BaseFragment {
    ProgressBar progress;
    Button btn;
    int progressPercentage = 0;
    int currentItem = 0;

    TextView question;
    MaterialRadioButton q1, q2, q3, q4;
    RadioGroup group;
    ArrayList<fatemeQuestion> myArr = new ArrayList<>();

    public FatemeQuestionFragment() {
    }

    public static FatemeQuestionFragment newInstance() {
        FatemeQuestionFragment fragment = new FatemeQuestionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fateme_question, container, false);
        GetMyViews(v);
        getQuestion();
        ShuffleQuestions();
        setOnclickListener();
        FillData();

//        ((MainActivity)getActivity()).DoManaWorker(getActivity(),"کاربر وارد تب آواتار  شد","enterAvatar");
        return v;
    }

    private void ShuffleQuestions() {
        Collections.shuffle(myArr);
    }

    private void getQuestion() {

        myArr.add(new fatemeQuestion("بررسی\u200Cهای روزانه دیابت شامل  کدام یک از موارد زیر است؟", "معاینه پاها", "اندازه\u200Cگیری قند خون", "هردومورد", "", "تعداد دفعات اندازه\u200Cگیری قند خون و اینکه آیا لازم است شما هر روز این کار را انجام بدهید یا خیر، مسئله\u200Cای است که حتماً باید با نظر پزشک و به صورت اختصاصی برای هر فرد بسته به نوع شرایط و داروی کاهش دهنده قند خون مصرفی، تعیین شود.", 2));
        myArr.add(new fatemeQuestion("با توجه به نظر پزشکتان آزمایش هموگلوبین ای وان سی (HbA1c) را هرچند یکبار باید انجام  دادد؟ ", "3 تا 6 ماه یکبار", "سالانه", "", "", "در هر ویزیت پزشکی که معمولاً بسته به نظر پزشک معالج هر 3 تا 6 ماه یکبار است، باید فشار خونتان نیز بررسی شود. ", 0));
        myArr.add(new fatemeQuestion("بزرگسالان مبتلا به دیابت نوع 2 بعد از تشخیص، حداقل سالی یک بار باید کدامیک از چکاپ های زیررا انجام دهند ؟", "آلبومین ادرار", "معاینه شبکیه", "بررسی نوروپاتی محیطی", "هرسه مورد", "", 3));

    }

    private void FillData() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            question.setText(HtmlCompat.fromHtml(myArr.get(currentItem).getQ(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
        question.setText(myArr.get(currentItem).getQuestion());
//        }

//        question.setText(Html.fromHtml("<u>دشواری در تصمیم گیری</u> مربوط به کدام فاز اختلال دوقطبی می باشد؟"));

        SettextForRadioGroup(q1, myArr.get(currentItem).getGozine1());
        SettextForRadioGroup(q2, myArr.get(currentItem).getGozine2());
        SettextForRadioGroup(q3, myArr.get(currentItem).getGozine3());
        SettextForRadioGroup(q4, myArr.get(currentItem).getGozine4());

    }

    private void SettextForRadioGroup(MaterialRadioButton q1, String matn) {
        if (matn.isEmpty())
            q1.setVisibility(View.GONE);
        else
            q1.setText(matn);
    }

    private void MakeEverythingVisible() {
        q1.setVisibility(View.VISIBLE);
        q2.setVisibility(View.VISIBLE);
        q3.setVisibility(View.VISIBLE);
        q4.setVisibility(View.VISIBLE);
    }


    private void setOnclickListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCorrectAnswer(group.getCheckedRadioButtonId()) == myArr.get(currentItem).getAnswer()) {
                    //its ok goto next question

                    if (myArr.get(currentItem).getMsg().isEmpty())
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText("جواب شما صحیح است").show();
                    else
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText(myArr.get(currentItem).getMsg()).show();

                    itsOkGoToNextQuestion();

                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("اشتباه").setConfirmText("باشه").setContentText("جواب شما غلط است").show();


                }

            }
        });
    }

    private int getCorrectAnswer(int RadioId) {
        switch (RadioId) {
            case R.id.q1:

                return 0;
            case R.id.q2:
                return 1;

            case R.id.q3:
                return 2;

        }
        return 3;
    }

    private void itsOkGoToNextQuestion() {
        MakeEverythingVisible();
        progressPercentage += 33;
        group.clearCheck();
        progress.setProgress(progressPercentage);
        if ((currentItem + 1) == myArr.size()) {
            Log.wtf("here", "in currentitem+1");
            progressPercentage = 0;
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText("شما به تمام سوالات درست جواب داده اید.");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
//                    AddUp(getActivity(), "quiz");
                    getActivity().finish();
                }
            });
            sweetAlertDialog.show();
        } else {
            currentItem++;
            FillData();
        }
    }

    private void GetMyViews(View v) {
        btn = (Button) v.findViewById(R.id.btn);
        progress = (ProgressBar) v.findViewById(R.id.progress);
        group = (RadioGroup) v.findViewById(R.id.group);
        q1 = (MaterialRadioButton) v.findViewById(R.id.q1);
        q2 = (MaterialRadioButton) v.findViewById(R.id.q2);
        q3 = (MaterialRadioButton) v.findViewById(R.id.q3);
        q4 = (MaterialRadioButton) v.findViewById(R.id.q4);
        question = (TextView) v.findViewById(R.id.question);

    }
}
