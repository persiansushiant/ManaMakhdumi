package com.mrgamification.manamakhdumi.adapter;

import static android.content.Context.ALARM_SERVICE;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.mrgamification.manamakhdumi.reciever.AlarmReceiver;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;

import java.util.ArrayList;

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
            card = (CardView) view.findViewById(R.id.card);
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
        ImageButton setting=holder.setting;
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

                        AlarmManager alarmManager = (AlarmManager) mCOntext.getSystemService(ALARM_SERVICE);

                        Intent intent = new Intent(mCOntext, AlarmReceiver.class);

                        intent.putExtra("whatID", model.getId() + "");
                        intent.putExtra("status",  "1");

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(mCOntext, model.getId().intValue(), intent, PendingIntent.FLAG_IMMUTABLE);
                        alarmManager.cancel(pendingIntent);


                        myArr.remove(model);

                        updateAdapter(myArr);
                        DaruItem.deleteAll(DaruItem.class, "id=?", model.getId() + "");


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

