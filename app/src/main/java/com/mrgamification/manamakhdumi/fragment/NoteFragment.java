package com.mrgamification.manamakhdumi.fragment;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.adapter.DaruAdapter;
import com.mrgamification.manamakhdumi.adapter.NoteAdapter;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {
    RecyclerView myRecyclerView;
    FloatingActionButton fab;
    ArrayList<Note> myArr = new ArrayList<>();
    TextView txt;
    NoteAdapter daruAdapter;


    public NoteFragment() {
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        getViews(v);
        RefreshList();
        showList();

        return v;
    }

    private void getViews(View v) {
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        txt = (TextView) v.findViewById(R.id.txt);
    }

    private void showList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        daruAdapter = new NoteAdapter(getActivity(), myArr);
        myRecyclerView.setAdapter(daruAdapter);

    }

    private void RefreshList() {
        getData();
        CheckData();
    }
    private void getData() {
        List<Note> daruItemList = DaruItem.listAll(Note.class);
        myArr.clear();
        for (int i = 0; i < daruItemList.size(); i++) {
            myArr.add(daruItemList.get(i));

        }

    }

    private void CheckData() {
        if (myArr.size() == 0) {
            myRecyclerView.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
        } else {

            myRecyclerView.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);
            showList();

        }
    }
}
