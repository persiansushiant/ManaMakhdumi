package com.mrgamification.manamakhdumi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.fragment.FatemeQuestionFragment;
import com.mrgamification.manamakhdumi.model.Badge;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.chooseItem;
import com.mrgamification.manamakhdumi.sweetalertdialog.SweetAlertDialog;
import com.mrgamification.manamakhdumi.textdrawable.TextDrawable;
import com.mrgamification.manamakhdumi.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

public class chooseItemAdapter extends RecyclerView.Adapter<chooseItemAdapter.ViewHolder> {
    MainActivity activity;
    ArrayList<chooseItem> myArr;
    Context mCOntext;

    public chooseItemAdapter(ArrayList<chooseItem> myArr, Context mCOntext) {
        this.mCOntext = mCOntext;
        this.myArr = myArr;
        activity = (MainActivity) mCOntext;
    }

    public void updateAdapter(ArrayList<chooseItem> myArr) {
        this.myArr = myArr;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return myArr.size();

    }


    @Override
    public chooseItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chooseitem_row, parent, false);
        return new chooseItemAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final chooseItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();


        holder.txt.setText(myArr.get(position).getName());
        holder.linearLayout.setBackgroundColor(color1);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myArr.get(position).isTrue()) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mCOntext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("موفقیت").
                            setContentText(myArr.get(position).getName() + " شامل مهارت های مدیریت دیابت است.").setConfirmText("باشه");

                    sweetAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            myArr.remove(position);
                            updateAdapter(myArr);
                            if (!StillWeHaveSomething()) {


                                FatemeQuestionFragment fatemeQuestionFragment = FatemeQuestionFragment.newInstance();
                                ((MainActivity) mCOntext).showFragment(fatemeQuestionFragment);
                            }
                        }
                    });
                    sweetAlertDialog.show();
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mCOntext, SweetAlertDialog.ERROR_TYPE).setTitleText("اشتباه").
                            setContentText(myArr.get(position).getName() + " شامل مهارت های مدیریت دیابت نیست.").setConfirmText("باشه");
                    sweetAlertDialog.show();

                }
            }
        });


    }

    private boolean StillWeHaveSomething() {
        for (chooseItem item : myArr) {
            if (item.isTrue())
                return true;
        }
        return false;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;
        LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            txt = (TextView) v.findViewById(R.id.txt);
            linearLayout = (LinearLayout) v.findViewById(R.id.linlin);

        }


    }


}



