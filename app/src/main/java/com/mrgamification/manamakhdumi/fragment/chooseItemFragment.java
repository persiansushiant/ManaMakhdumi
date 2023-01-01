package com.mrgamification.manamakhdumi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.adapter.DaruAdapter;
import com.mrgamification.manamakhdumi.adapter.chooseItemAdapter;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.chooseItem;
import com.mrgamification.manamakhdumi.textdrawable.TextDrawable;
import com.mrgamification.manamakhdumi.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Collections;

public class chooseItemFragment   extends BaseFragment {
    ArrayList<chooseItem> myArr = new ArrayList<>();
    RecyclerView myRecyclerView;
    chooseItemAdapter chooseItemFragmentAdapter;

    public chooseItemFragment() {
    }

    public static chooseItemFragment newInstance() {
        chooseItemFragment fragment = new chooseItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chooseitem, container, false);
        findViews(v);
        fillData();

        return v;
    }

    private void fillData() {
        myArr.add(new chooseItem("تغذیه اصولی در دیابت و شمارش کربوهیدرات", true));
        myArr.add(new chooseItem("فعالیت بدنی", true));
        myArr.add(new chooseItem("مدیریت استرس", true));
        myArr.add(new chooseItem("اصول نگهداری و مصرف داروها", true));
        myArr.add(new chooseItem("تست قند صحیح", true));
        myArr.add(new chooseItem("محدود کردن تغذیه", false));
        myArr.add(new chooseItem("ورزش های مکرر  وزیاد برای کاهش وزن", false));
        myArr.add(new chooseItem("مصرف خودسرانه داروها بدون نظر پزشک", false));
        Collections.shuffle(myArr);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chooseItemFragmentAdapter = new chooseItemAdapter(myArr,getActivity());
        myRecyclerView.setAdapter(chooseItemFragmentAdapter);


    }

    private void setTextForMe(ImageView image, String s) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(s, color1, 10);
        image.setImageDrawable(drawable);



    }

    private void findViews(View v) {
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

    }


}