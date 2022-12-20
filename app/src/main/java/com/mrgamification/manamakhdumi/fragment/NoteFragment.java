package com.mrgamification.manamakhdumi.fragment;

import static android.content.Context.ALARM_SERVICE;

import static com.mrgamification.manamakhdumi.activity.BaseActivity.AddUp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
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
    boolean isChecked = true;


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
        SetOnCliclListener();
//        ((MainActivity) getActivity()).DoManaWorker(getActivity(), "کاربر وارد تب یادداشت  شد", "enterNoteTab");

        return v;
    }

    private void SetOnCliclListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.note_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                EditText et = (EditText) dialog.findViewById(R.id.et);
                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                Button p1 = (Button) dialog.findViewById(R.id.p1);
                Button p2 = (Button) dialog.findViewById(R.id.p2);
                p1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p1.setBackgroundColor(getResources().getColor(R.color.md_green_A400));
                        p2.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
                        isChecked = false;

                    }
                });
                p2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p2.setBackgroundColor(getResources().getColor(R.color.md_green_A400));
                        p1.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
                        isChecked = true;

                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (et.getText().toString().length() == 0) {
                            et.setError("لطفا متنی وارد کنید.");
                        } else {
                            Note myNote = new Note(et.getText().toString(), isChecked);

//                            AddUp(getActivity(), "note");
                            ((MainActivity) getActivity()).DoManaWorker(getActivity(),
                                    et.getText().toString(), "addNote");
                            myNote.save();
                            myArr.add(myNote);
                            RefreshList();
                            dialog.dismiss();
                        }

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
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
