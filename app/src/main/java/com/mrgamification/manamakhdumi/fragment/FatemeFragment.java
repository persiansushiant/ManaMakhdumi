package com.mrgamification.manamakhdumi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;
import com.mrgamification.manamakhdumi.textdrawable.TextDrawable;
import com.mrgamification.manamakhdumi.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class FatemeFragment extends BaseFragment implements View.OnClickListener {
    ImageView t1, t2, t3, t4, t5, t6, t7, t8;
    TextView javab;
    Button checkButton;
    ArrayList<String> myStrings = new ArrayList<>();
    ArrayList<String> myArr = new ArrayList<>();
    HashMap<Integer, String> hmap = new HashMap<Integer, String>();

    public FatemeFragment() {
    }

    public static FatemeFragment newInstance() {
        FatemeFragment fragment = new FatemeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fateme, container, false);
        findViews(v);
        FirstDataInserted();
        FillArray(myStrings.get(0));
        FillTextViews();

//        ((MainActivity)getActivity()).DoManaWorker(getActivity(),"کاربر وارد تب آواتار  شد","enterAvatar");
        return v;
    }


    private void MakeEveryThingVIsible() {
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);
        t4.setVisibility(View.VISIBLE);
        t5.setVisibility(View.VISIBLE);
        t6.setVisibility(View.VISIBLE);
        t7.setVisibility(View.VISIBLE);
        t8.setVisibility(View.VISIBLE);
    }

    private void FirstDataInserted() {
        myStrings.add("خربزه");
        myStrings.add("موز");
        myStrings.add("انگور");
        myStrings.add("طالبی");
        myStrings.add("هندوانه");
        Collections.shuffle(myStrings);
    }

    private void FillTextViews() {
        int lens = myArr.size();
        switch (lens) {
            case 3:
                setTextForMe(t1, myArr.get(0));
                t2.setVisibility(View.GONE);
                setTextForMe(t3, myArr.get(1));
                setTextForMe(t4, myArr.get(2));
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t7.setVisibility(View.GONE);
                t8.setVisibility(View.GONE);


                hmap.put(1, myArr.get(0));
                hmap.put(3, myArr.get(1));
                hmap.put(4, myArr.get(2));

                break;
            case 4:
                setTextForMe(t1, myArr.get(0));
                setTextForMe(t2, myArr.get(1));
                setTextForMe(t3, myArr.get(2));
                setTextForMe(t4, myArr.get(3));
                t5.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t7.setVisibility(View.GONE);
                t8.setVisibility(View.GONE);
                hmap.put(1, myArr.get(0));
                hmap.put(2, myArr.get(1));
                hmap.put(3, myArr.get(2));
                hmap.put(4, myArr.get(3));

                break;

            case 5:
                setTextForMe(t1, myArr.get(0));
                setTextForMe(t2, myArr.get(1));
                setTextForMe(t3, myArr.get(2));
                t4.setVisibility(View.GONE);
                setTextForMe(t5, myArr.get(3));
                setTextForMe(t6, myArr.get(4));
                t7.setVisibility(View.GONE);
                t8.setVisibility(View.GONE);

                hmap.put(1, myArr.get(0));
                hmap.put(2, myArr.get(1));
                hmap.put(3, myArr.get(2));
                hmap.put(5, myArr.get(3));
                hmap.put(6, myArr.get(4));

                break;

            case 6:
                setTextForMe(t1, myArr.get(0));
                setTextForMe(t2, myArr.get(1));
                setTextForMe(t3, myArr.get(2));
                setTextForMe(t4, myArr.get(3));
                setTextForMe(t5, myArr.get(4));
                setTextForMe(t6, myArr.get(5));
                t7.setVisibility(View.GONE);
                t8.setVisibility(View.GONE);
                hmap.put(1, myArr.get(0));
                hmap.put(2, myArr.get(1));
                hmap.put(3, myArr.get(2));
                hmap.put(4, myArr.get(3));
                hmap.put(5, myArr.get(4));
                hmap.put(6, myArr.get(5));

                break;

            case 7:
                setTextForMe(t1, myArr.get(0));
                t2.setVisibility(View.GONE);
                setTextForMe(t3, myArr.get(1));
                setTextForMe(t4, myArr.get(2));
                setTextForMe(t5, myArr.get(3));
                setTextForMe(t6, myArr.get(4));
                setTextForMe(t7, myArr.get(5));
                setTextForMe(t8, myArr.get(6));
                hmap.put(1, myArr.get(0));
                hmap.put(3, myArr.get(1));
                hmap.put(4, myArr.get(2));
                hmap.put(5, myArr.get(3));
                hmap.put(6, myArr.get(4));
                hmap.put(7, myArr.get(5));
                hmap.put(8, myArr.get(6));

                break;
            default:

                break;

        }
    }

    private void setTextForMe(ImageView image, String s) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
        int color1 = generator.getRandomColor();
//// generate color based on a key (same key returns the same color), useful for list/grid views
//
//// declare the builder object once.
//        TextDrawable.IBuilder builder = TextDrawable.builder()
//                .beginConfig()
//                .withBorder(4)
//                .endConfig()
//                .round();
//        TextDrawable ic1 = builder.build(s, color1);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(200)  // width in px
                .height(200) // height in px
                .endConfig()
                .buildRound(s, color1);
        image.setImageDrawable(drawable);


//        image.setImageDrawable(getResources().getDrawable(R.drawable.gamee));

    }

    private void FillArray(String s) {
        for (int i = 0; i < s.length(); i++) {
            myArr.add(s.charAt(i) + "");
        }
        Collections.shuffle(myArr);
    }

    private void findViews(View v) {
        t1 = (ImageView) v.findViewById(R.id.t1);
        t2 = (ImageView) v.findViewById(R.id.t2);
        t3 = (ImageView) v.findViewById(R.id.t3);
        t4 = (ImageView) v.findViewById(R.id.t4);
        t5 = (ImageView) v.findViewById(R.id.t5);
        t6 = (ImageView) v.findViewById(R.id.t6);
        t7 = (ImageView) v.findViewById(R.id.t7);
        t8 = (ImageView) v.findViewById(R.id.t8);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        javab = (TextView) v.findViewById(R.id.javab);
        checkButton = (Button) v.findViewById(R.id.check);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myStrings.get(0).equalsIgnoreCase(javab.getText().toString())) {
                    javab.setText("");
                    MakeEveryThingVIsible();
                    MakeEveryThingClickable();

                    myStrings.remove(0);
                    myArr.clear();
                    if (myStrings.size() > 0) {
                        FillArray(myStrings.get(0));
                        FillTextViews();
                    } else {


                        TruFalseFragment truFalseFragment = TruFalseFragment.newInstance();
                        ((MainActivity)getActivity()).showFragment(truFalseFragment);
                    }

                } else if (javab.getText().toString().length() == 0) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("خالی").setContentText("با کلیک روی حروف جواب را انتخاب کنید.")
                            .setConfirmText("باشه").show();
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("اشتباه").setContentText("جواب شما اشتباه است.")
                            .setConfirmText("باشه").show();
                    javab.setText("");
                    MakeEveryThingClickable();

                }
            }
        });

    }

    private void MakeEveryThingClickable() {
        t1.setClickable(true);
        t2.setClickable(true);
        t3.setClickable(true);
        t4.setClickable(true);
        t5.setClickable(true);
        t6.setClickable(true);
        t7.setClickable(true);
        t8.setClickable(true);
    }


    @Override
    public void onClick(View v) {
        v.setClickable(false);
        String s = javab.getText().toString();

        switch (v.getId()) {
            case R.id.t1:
                javab.setText(s + hmap.get(1));

                break;
            case R.id.t2:
                javab.setText(s + hmap.get(2));

                break;

            case R.id.t3:
                javab.setText(s + hmap.get(3));

                break;
            case R.id.t4:
                javab.setText(s + hmap.get(4));

                break;
            case R.id.t5:
                javab.setText(s + hmap.get(5));

                break;
            case R.id.t6:
                javab.setText(s + hmap.get(6));

                break;
            case R.id.t7:
                javab.setText(s + hmap.get(7));

                break;
            case R.id.t8:
                javab.setText(s + hmap.get(8));

                break;

        }
    }
}
