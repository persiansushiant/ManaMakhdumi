package com.mrgamification.manamakhdumi.fragment;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;

import static com.mrgamification.manamakhdumi.activity.BaseActivity.PrettifyMyTIme;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.getManaUser;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.setAlarmForMe;
import static com.mrgamification.manamakhdumi.application.MyApplication.delay;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.BaseActivity;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.adapter.DaruAdapter;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.DefferedMana;
import com.mrgamification.manamakhdumi.model.Question;
import com.mrgamification.manamakhdumi.model.faramushi;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.reciever.MyService;
import com.mrgamification.manamakhdumi.service.ForegroundService;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DaruFragment extends BaseFragment {
    RecyclerView myRecyclerView;
    FloatingActionButton fab;
    ArrayList<DaruItem> myArr = new ArrayList<>();
    TextInputEditText drugName;
    TextInputEditText drugDuration;
    TimePicker timePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    DaruAdapter daruAdapter;
    TextInputLayout t1, t2;
    TextView txt;

    public DaruFragment() {
    }

    public static DaruFragment newInstance() {
        DaruFragment fragment = new DaruFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daru, container, false);
        getViews(v);
        ClickListeners();
        RefreshList();
        showList();
//        ((MainActivity) getActivity()).DoManaWorker(getActivity(), "کاربر وارد تب دارو  شد", "enterDaruTab");
        return v;
    }


    private void showList() {
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        daruAdapter = new DaruAdapter(getActivity(), myArr);
        myRecyclerView.setAdapter(daruAdapter);

    }

    private void RefreshList() {
        getData();
        CheckData();
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

    private void getData() {
        List<DaruItem> daruItemList = DaruItem.listAll(DaruItem.class);
        myArr.clear();
        for (int i = 0; i < daruItemList.size(); i++) {
            myArr.add(daruItemList.get(i));

        }

    }


    private void ClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                Button btn;
                drugName = (TextInputEditText) dialog.findViewById(R.id.drugName);
                drugDuration = (TextInputEditText) dialog.findViewById(R.id.drugDuration);
                btn = (Button) dialog.findViewById(R.id.btn);
                t1 = (TextInputLayout) dialog.findViewById(R.id.t1);
                t2 = (TextInputLayout) dialog.findViewById(R.id.t2);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (EveryThingIsOk()) {
                            dialog.dismiss();
                            Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.second_layout_dialog);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCancelable(true);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            Button btn;
                            timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                            btn = (Button) dialog.findViewById(R.id.btn);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startService();
                                    ravesheSabegh(dialog);

                                    dialog.dismiss();
                                }
                            });
                            dialog.show();


                        } else {
                            dialog.dismiss();
                            alarmManager.cancel(pendingIntent);

                        }
                        dialog.dismiss();


                    }
                });


                dialog.show();


            }
        });
    }

    public void startService() {
        Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    private void ravesheSabegh(Dialog dialog) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            long time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                // setting time as AM and PM
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            DaruItem daruItem = new DaruItem(drugName.getText().toString(), drugDuration.getText().toString(), time + "", 1);
            myArr.add(daruItem);
            daruItem.save();
            ((MainActivity) getActivity()).DoManaWorker(getActivity(), "داروی " + daruItem.getDaruName() + " هر" + daruItem.getDaruLenght() + " ساعت اضاف شد"+"اولین الارم در ساعت"+PrettifyMyTIme(daruItem.getNextStop()+"")
                    , "addDaru");


            RefreshList();
            setAlarmForMe(getActivity(), daruItem);

            dialog.dismiss();

        } catch (Exception e) {
            Log.wtf("error", e.getMessage());
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("اینو برای من عکس بگیر بفرست").setContentText(e.getMessage()).show();
        }
    }


    private boolean EveryThingIsOk() {

        if (drugName.getText().length() == 0) {
            t1.setError("این فیلد را پر کنید.");
            return false;

        }

        if (drugDuration.getText().length() == 0 || drugDuration.getText().equals("0")) {
            t2.setError("این فیلد را پر کنید.");
            return false;

        }

        return true;
    }


    private void getViews(View v) {
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        txt = (TextView) v.findViewById(R.id.txt);
    }
}
