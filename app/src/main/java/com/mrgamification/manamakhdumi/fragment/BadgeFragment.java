package com.mrgamification.manamakhdumi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.adapter.BadgeAdapter;
import com.mrgamification.manamakhdumi.model.Badge;
import com.mrgamification.manamakhdumi.model.DaruItem;

import java.util.ArrayList;
import java.util.List;

public class BadgeFragment extends BaseFragment {
    RecyclerView myRecyclerView;
    ArrayList<Badge> myArr = new ArrayList<>();
    BadgeAdapter adapter;

    public BadgeFragment() {
    }

    public static BadgeFragment newInstance() {
        BadgeFragment fragment = new BadgeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_badge, container, false);
        getViews(v);
        getData();
        showList();

        return v;
    }


    private void showList() {
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new BadgeAdapter(myArr, getActivity());
        myRecyclerView.setAdapter(adapter);

    }


    private void getData() {
        List<Badge> daruItemList = DaruItem.listAll(Badge.class);
        Log.wtf("tedade badge",daruItemList.size()+"");
        for (int i = 0; i < daruItemList.size(); i++) {
            myArr.add(daruItemList.get(i));

        }

    }


    private void getViews(View v) {
        myRecyclerView = (RecyclerView) v.findViewById(R.id.myList);

    }
}
