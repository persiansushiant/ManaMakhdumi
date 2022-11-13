package com.mrgamification.manamakhdumi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.model.DaruItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvatarFragment extends BaseFragment {
    ImageView img;
    ArrayList<DaruItem> myArr = new ArrayList<>();

    public AvatarFragment() {
    }

    public static AvatarFragment newInstance() {
        AvatarFragment fragment = new AvatarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_avatar, container, false);
        img = (ImageView) v.findViewById(R.id.img);
        getData();
        SetState();

        return v;
    }

    private void SetState() {

        ArrayList<Integer> myNewArr = new ArrayList<Integer>();
        if (myArr.size() == 0)
            Glide.with(this).load(R.raw.happy).into(img);
        else {
            for (DaruItem daruItem : myArr) {
                myNewArr.add(daruItem.getStatus());

            }
            Integer max = Collections.max(myNewArr);


            if (max == 5) {
                Glide.with(this).load(R.raw.anxiety).into(img);
                return;

            } else if (max == 4) {
                Glide.with(this).load(R.raw.anxiety).into(img);

                return;

            } else if (max == 3) {

                Glide.with(this).load(R.raw.angry).into(img);
                return;

            } else if (max == 2) {

                Glide.with(this).load(R.raw.sad).into(img);
                return;

            } else {

                Glide.with(this).load(R.raw.happy).into(img);
                return;




            }

        }

    }

    private void getData() {
        List<DaruItem> daruItemList = DaruItem.listAll(DaruItem.class);
        myArr.clear();
        for (int i = 0; i < daruItemList.size(); i++) {
            myArr.add(daruItemList.get(i));

        }

    }
}
