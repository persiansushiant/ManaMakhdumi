package com.mrgamification.manamakhdumi.adapter;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.Note;
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;

public class NoteAdapter  extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context mCOntext;
    ArrayList<Note> myArr = new ArrayList<>();

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
            card = (CardView) view.findViewById(R.id.card);
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
        ImageButton setting=holder.setting;
        CardView card = holder.card;
        RelativeLayout myRel = holder.myRel;
        t1.setText(model.getText());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SweetAlertDialog mySweetAlertDialog = new SweetAlertDialog(mCOntext, SweetAlertDialog.ERROR_TYPE).setTitleText("حذف").
                        setConfirmText("بله");
                mySweetAlertDialog.setCancelText("خیر").setContentText("آیا از حذف این دارو مطمین هستید؟").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {


                        mySweetAlertDialog.dismiss();
                    }
                }).show();

            }
        });
    }


    public long getItemId(int position) {
        return (long) position;
    }


}

