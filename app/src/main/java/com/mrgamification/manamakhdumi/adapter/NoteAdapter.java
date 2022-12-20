package com.mrgamification.manamakhdumi.adapter;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.Note;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context mCOntext;
    ArrayList<Note> myArr = new ArrayList<>();
    boolean isChecked=false;


    public NoteAdapter(Context mCOntext2, ArrayList<Note> myArr2) {
        this.myArr = myArr2;
        this.mCOntext = mCOntext2;
    }

    public void updateAdapter(ArrayList<Note> myArr2) {
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


    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daru_row
                , parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        final Note model = myArr.get(position);
        TextView t1 = holder.t1;
        ImageButton delete = holder.delete;
        ImageButton setting = holder.setting;
        CardView card = holder.card;
        RelativeLayout myRel = holder.myRel;
        if (model.isSoal())
            t1.setText("مشکل دارویی");
        else
            t1.setText("مورد نیازمند گزارش");
        if (model.isSoal())
            myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_blue_400));
        else
            myRel.setBackgroundColor(ContextCompat.getColor(mCOntext, R.color.md_green_400));


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SweetAlertDialog mySweetAlertDialog = new SweetAlertDialog(mCOntext, SweetAlertDialog.ERROR_TYPE).setTitleText("حذف").
                        setConfirmText("بله");
                mySweetAlertDialog.setCancelText("خیر").setContentText("آیا از حذف این یادداشت مطمین هستید؟").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        myArr.remove(model);
                        ((MainActivity) mCOntext).DoManaWorker(mCOntext,
                                model.getText(), "deleteNote");
                        updateAdapter(myArr);

                        Note.deleteAll(Note.class, "id=?", model.getId() + "");
                        mySweetAlertDialog.dismiss();
                    }
                }).show();

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mCOntext);
                dialog.setContentView(R.layout.note_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                EditText et = (EditText) dialog.findViewById(R.id.et);
                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                et.setText(model.getText());
                Button p1=(Button)dialog.findViewById(R.id.p1);
                Button p2=(Button)dialog.findViewById(R.id.p2);

                        if (model.isSoal()) {
                            p2.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_green_A400));
                            p1.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_white_1000));
                        } else {
                            p1.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_green_A400));
                            p2.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_white_1000));
                        }
                p1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p1.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_green_A400));
                        p2.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_white_1000));
                        isChecked=false;

                    }
                });
                p2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p2.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_green_A400));
                        p1.setBackgroundColor(mCOntext.getResources().getColor(R.color.md_white_1000));
                        isChecked=true;

                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (et.getText().toString().length() == 0) {
                            et.setError("لطفا متنی وارد کنید.");
                        }else {
                            model.setSoal(isChecked);
                            model.setText(et.getText().toString());
                            Note.saveInTx(model);
                            updateAdapter(myArr);
                            ((MainActivity) mCOntext).DoManaWorker(mCOntext,
                                    et.getText().toString(), "editNote");
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


    public long getItemId(int position) {
        return (long) position;
    }


}

