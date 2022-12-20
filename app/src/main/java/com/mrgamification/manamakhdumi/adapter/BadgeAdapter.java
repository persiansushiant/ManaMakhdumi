package com.mrgamification.manamakhdumi.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrgamification.manamakhdumi.R;
import com.mrgamification.manamakhdumi.activity.MainActivity;
import com.mrgamification.manamakhdumi.model.Badge;

import java.util.ArrayList;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {
    MainActivity activity;
    ArrayList<Badge> myArr;
    Context mCOntext;

    public BadgeAdapter(ArrayList<Badge> myArr, Context mCOntext) {
        this.mCOntext = mCOntext;
        this.myArr = myArr;
        activity = (MainActivity) mCOntext;
    }


    @Override
    public int getItemCount() {

        return myArr.size();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String tedad = mCOntext.getApplicationContext().getSharedPreferences("movakel", 0).getString(myArr.get(position).getAtion(), "1");
        int myTedad = Integer.parseInt(tedad);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        Resources resources = mCOntext.getResources();
        final int resourceId = resources.getIdentifier(myArr.get(position).getName(), "drawable",
                mCOntext.getPackageName());

        Glide.with(mCOntext).load(resources.getDrawable(resourceId)).into(holder.image);
        if (myTedad <= myArr.get(position).getTedad()) {
            holder.image.setColorFilter(filter);

        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;


        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);


        }


    }


}



