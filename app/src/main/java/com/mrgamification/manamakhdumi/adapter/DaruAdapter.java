package com.mrgamification.manamakhdumi.adapter;

import static android.content.Context.ALARM_SERVICE;


import static com.mrgamification.manamakhdumi.activity.BaseActivity.cancelAlarmForMe;
import static com.mrgamification.manamakhdumi.activity.BaseActivity.setAlarmForMe;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DaruAdapter extends RecyclerView.Adapter<DaruAdapter.ViewHolder> {
    Context mCOntext;
    ArrayList<DaruItem> myArr = new ArrayList<>();

    public DaruAdapter(Context mCOntext2, ArrayList<DaruItem> myArr2) {
        this.myArr = myArr2;
        this.mCOntext = mCOntext2;
    }

    public void updateAdapter(ArrayList<DaruItem> myArr2) {
        this.myArr = myArr2;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return this.myArr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageButton delete;
        ImageButton setting;
        CardView card;
        RelativeLayout myRel;

        public ViewHolder(View view) {
            super(view);
            t1 = (TextView) view.findViewById(R.id.t1);
            delete = (ImageButton) view.findViewById(R.id.delete);
            setting = (ImageButton) view.findViewById(R.id.setting);
            myRel = (RelativeLayout) view.findViewById(R.id.myRel);
        }


    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daru_row
                , parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DaruItem model = myArr.get(position);
        TextView t1 = holder.t1;
        ImageButton delete = holder.delete;
        ImageButton setting = holder.setting;
        CardView card = holder.card;
        RelativeLayout myRel = holder.myRel;
        t1.setText(model.getDaruName());

        switch (model.getStatus()) {
            case 1:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_blue_50));
                break;
            case 2:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_blue_100));

                break;
            case 3:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_red_200));

                break;
            case 4:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_red_300));

                break;
            case 5:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_red_400));
                break;
            default:
                myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_red_500));

                break;
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SweetAlertDialog mySweetAlertDialog = new SweetAlertDialog(mCOntext, SweetAlertDialog.ERROR_TYPE).setTitleText("حذف").
                        setConfirmText("بله");
                mySweetAlertDialog.setCancelText("خیر").setContentText("آیا از حذف این دارو مطمین هستید؟").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        cancelAlarmForMe(mCOntext,model);

                        ((MainActivity) mCOntext).DoManaWorker(mCOntext,
                                model.getDaruName()+" حذف شد", "removeDaru");
                        myArr.remove(model);

                        updateAdapter(myArr);
                        DaruItem.deleteAll(DaruItem.class, "id=?", model.getId() + "");


                        mySweetAlertDialog.dismiss();
                    }
                }).show();

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(mCOntext);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                Button btn;
                TextInputEditText drugName = (TextInputEditText) dialog.findViewById(R.id.drugName);
                TextInputEditText drugDuration = (TextInputEditText) dialog.findViewById(R.id.drugDuration);
                btn = (Button) dialog.findViewById(R.id.btn);
                TextInputLayout t1 = (TextInputLayout) dialog.findViewById(R.id.t1);
                TextInputLayout t2 = (TextInputLayout) dialog.findViewById(R.id.t2);
                drugName.setText(model.getDaruName());
                drugDuration.setText(model.getDaruLenght() + "");


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (EveryThingIsOk(t1, t2, drugName, drugDuration)) {
                            Log.wtf("is ok", "everything is ok");
                            dialog.dismiss();
                            Dialog dialog2 = new Dialog(mCOntext);
                            dialog2.setContentView(R.layout.second_layout_dialog);
                            dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog2.setCancelable(true);
                            dialog2.getWindow().getAttributes().windowAnimations = R.style.animation;

                            TimePicker timePicker = (TimePicker) dialog2.findViewById(R.id.timePicker);
                            Calendar myCal = Calendar.getInstance();
                            myCal.setTime(new Date(Long.parseLong(model.getNextStop())));
                            ((MainActivity) mCOntext).DoManaWorker(mCOntext,
                                    model.getDaruName()+" ادیت شد", "editDaru");

                            timePicker.setIs24HourView(false);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                timePicker.setHour(myCal.get(Calendar.HOUR));
                                timePicker.setMinute(myCal.get(Calendar.MINUTE));

                            }
                           Button btnz = (Button) dialog2.findViewById(R.id.btn);
                            btnz.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.wtf("btnz","ok");
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
                                    model.setNextStop(time + "");
                                    model.setDaruName(drugName.getText().toString());
                                    model.setDaruLenght(drugDuration.getText().toString());
                                    DaruItem.saveInTx(model);
                                    updateAdapter(myArr);
                                    cancelAlarmForMe(mCOntext,model);
                                    setAlarmForMe(mCOntext,model);
                                    dialog2.dismiss();
                                }
                            });
                            dialog2.show();


                        } else {


                        }
                        dialog.dismiss();


                    }
                });


                dialog.show();

            }
        });
    }

    private boolean EveryThingIsOk(TextInputLayout t1, TextInputLayout t2, TextInputEditText drugName, TextInputEditText drugDuration) {
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


    public long getItemId(int position) {
        return (long) position;
    }


}

