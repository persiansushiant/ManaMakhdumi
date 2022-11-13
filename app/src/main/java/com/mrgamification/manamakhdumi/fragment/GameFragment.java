package com.mrgamification.manamakhdumi.fragment;

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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.mrgamification.manamakhdumi.MainActivity;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.model.Question;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Collections;


public class GameFragment extends BaseFragment {
    ProgressBar progress;
    Button btn;
    int progressPercentage = 0;
    int currentItem = 0;
    String[] questions;
    String[] answers;
    String[] javab;
    TextView question;
    MaterialRadioButton q1, q2, q3;
    RadioGroup group;
    ArrayList<Question> myArr = new ArrayList<>();


    public GameFragment() {
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        GetMyViews(v);
        getQuestion();
        ShuffleQuestions();
        setOnclickListener();
        FillData();

        return v;
    }

    private void ShuffleQuestions() {
        Collections.shuffle(myArr);
    }

    private void getQuestion() {

        for (int i = 0; i < questions.length; i++) {
            myArr.add(new Question(questions[i], answers[i * 3], answers[i * 3 + 1], answers[i * 3 + 2], Integer.parseInt(javab[i])));
        }
        Log.wtf("size question", questions.length + "");
        Log.wtf("size answers", answers.length + "");
        Log.wtf("size javab", javab.length + "");
        Log.wtf("size myArr", myArr.size() + "");
    }

    private void FillData() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            question.setText(HtmlCompat.fromHtml(myArr.get(currentItem).getQ(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
        Log.wtf("html", myArr.get(currentItem).getQ());
        question.setText(fromHtml(myArr.get(currentItem).getQ()));
//        }

//        question.setText(Html.fromHtml("<u>دشواری در تصمیم گیری</u> مربوط به کدام فاز اختلال دوقطبی می باشد؟"));
        q1.setText(myArr.get(currentItem).getJ1());
        q2.setText(myArr.get(currentItem).getJ2());
        q3.setText(myArr.get(currentItem).getJ3());


    }

    public static Spanned fromHtml(String html) {
        if (html == null) {
            // return an empty spannable if the html is null
            return new SpannableString("");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    private void setOnclickListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myArr.get(currentItem).getAnswer() == 5) {

                    if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 1) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("اشتباه").setConfirmText("باشه").setContentText("گرچه ممکن است افزایش انرژی در فازشیدایی برای فرد جذاب باشد  اما می تواند باعث تحریک پذیری سریع، رفتارهای غیرقابل پیش بینی، پذیرش ریسک های غیرمعمول و انجام کارهایی که معمولاً انجام نمی\u200Cدهند شود که گاه عواقبی را برجای می\u200Cگذارد که ممکن است ماه\u200Cها یا سال\u200Cها درگیر اثرات آن باشند.").show();
                        itsOkGoToNextQuestion();
                    } else if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 2) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText("گرچه ممکن است افزایش انرژی در فازشیدایی برای فرد جذاب باشد  اما می تواند باعث تحریک پذیری سریع، رفتارهای غیرقابل پیش بینی، پذیرش ریسک های غیرمعمول و انجام کارهایی که معمولاً انجام نمی\u200Cدهند شود که گاه عواقبی را برجای می\u200Cگذارد که ممکن است ماه\u200Cها یا سال\u200Cها درگیر اثرات آن باشند.").show();
                        itsOkGoToNextQuestion();
                    } else if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 3) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText(" ").setConfirmText("باشه").setContentText("گرچه ممکن است افزایش انرژی در فازشیدایی برای فرد جذاب باشد  اما می تواند باعث تحریک پذیری سریع، رفتارهای غیرقابل پیش بینی، پذیرش ریسک های غیرمعمول و انجام کارهایی که معمولاً انجام نمی\u200Cدهند شود که گاه عواقبی را برجای می\u200Cگذارد که ممکن است ماه\u200Cها یا سال\u200Cها درگیر اثرات آن باشند.").show();
                        itsOkGoToNextQuestion();
                    }
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("با تشکر از نظر شما").setConfirmText("باشه").setContentText("گرچه ممکن است افزایش انرژی در فازشیدایی برای فرد جذاب باشد  اما می تواند باعث تحریک پذیری سریع، رفتارهای غیرقابل پیش بینی، پذیرش ریسک های غیرمعمول و انجام کارهایی که معمولاً انجام نمی\u200Cدهند شود که گاه عواقبی را برجای می\u200Cگذارد که ممکن است ماه\u200Cها یا سال\u200Cها درگیر اثرات آن باشند.").show();
                    itsOkGoToNextQuestion();

                } else if (myArr.get(currentItem).getAnswer() == 6) {

                    if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 1) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("با تشکر از نظر شما").setConfirmText("باشه").setContentText("درمان این بیماری ٫ اغلب به افراد این امکان را می دهد که واضح تر فکر کنند و عملکرد آن ها را بهبود می بخشد. \n" +
                                "بخشی از صحبت ها ی یک نویسنده نامزد جایزه پولیتزر:\n" +
                                "وقتی روی کتاب دومم کار می کردم  و حدود ۳۰۰۰ صفحه از آن را نوشته بودم، متوجه شدم بدترین کتابی که میتوانید تصور کنید را نوشتم و حس کردم قادر به اتمامش نیستم. در همین زمان بود که  تشخیص داده شد مبتلا به اختلال دو قطبی هستم فکر کردم که دیگر نمی توانم بنویسم ولی هنگامی که تحت درمان قرار گرفتم توانستم خلاقیتم را به طور موثر هدایت کنم و تمرکزم را بیشتر کنم. و الان مشغول نوشتم هفتمین کتابم هستم.\n").show();
                        itsOkGoToNextQuestion();
                    } else if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 2) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("با تشکر از نظر شما").setConfirmText("باشه").setContentText("درمان این بیماری ٫ اغلب به افراد این امکان را می دهد که واضح تر فکر کنند و عملکرد آن ها را بهبود می بخشد. \n" +
                                "بخشی از صحبت ها ی یک نویسنده نامزد جایزه پولیتزر:\n" +
                                "وقتی روی کتاب دومم کار می کردم  و حدود ۳۰۰۰ صفحه از آن را نوشته بودم، متوجه شدم بدترین کتابی که میتوانید تصور کنید را نوشتم و حس کردم قادر به اتمامش نیستم. در همین زمان بود که  تشخیص داده شد مبتلا به اختلال دو قطبی هستم فکر کردم که دیگر نمی توانم بنویسم ولی هنگامی که تحت درمان قرار گرفتم توانستم خلاقیتم را به طور موثر هدایت کنم و تمرکزم را بیشتر کنم. و الان مشغول نوشتم هفتمین کتابم هستم.\n").show();
                        itsOkGoToNextQuestion();
                    } else if (getCorrectAnswer(group.getCheckedRadioButtonId()) == 3) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE).setTitleText("با تشکر از نظر شما").setConfirmText("باشه").setContentText("درمان این بیماری ٫ اغلب به افراد این امکان را می دهد که واضح تر فکر کنند و عملکرد آن ها را بهبود می بخشد. \n" +
                                "بخشی از صحبت ها ی یک نویسنده نامزد جایزه پولیتزر:\n" +
                                "وقتی روی کتاب دومم کار می کردم  و حدود ۳۰۰۰ صفحه از آن را نوشته بودم، متوجه شدم بدترین کتابی که میتوانید تصور کنید را نوشتم و حس کردم قادر به اتمامش نیستم. در همین زمان بود که  تشخیص داده شد مبتلا به اختلال دو قطبی هستم فکر کردم که دیگر نمی توانم بنویسم ولی هنگامی که تحت درمان قرار گرفتم توانستم خلاقیتم را به طور موثر هدایت کنم و تمرکزم را بیشتر کنم. و الان مشغول نوشتم هفتمین کتابم هستم.\n").show();
                        itsOkGoToNextQuestion();
                    }




                } else if (getCorrectAnswer(group.getCheckedRadioButtonId()) == myArr.get(currentItem).getAnswer()) {
                    //its ok goto next question
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText("جواب شما صحیح است").show();

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

                return 1;
            case R.id.q2:
                return 2;

            case R.id.q3:
                return 3;

        }
        return 4;
    }

    private void itsOkGoToNextQuestion() {
        progressPercentage += 4;
        group.clearCheck();
        progress.setProgress(progressPercentage);
        if ((currentItem + 1) == questions.length) {
            Log.wtf("here", "in currentitem+1");
            progressPercentage = 0;
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").setConfirmText("باشه").setContentText("شما به تمام سوالات درست جواب داده اید.");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
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
        question = (TextView) v.findViewById(R.id.question);
        Resources res = getResources();
        answers = res.getStringArray(R.array.answers);
        questions = res.getStringArray(R.array.questions);
        javab = res.getStringArray(R.array.javab);
        Log.wtf("size", "size question " + questions.length + " size ansers " + answers.length + " javab size" + javab.length);
    }
}
