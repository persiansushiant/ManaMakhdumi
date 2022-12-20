package com.mrgamification.manamakhdumi.helper;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ManaWorker extends Worker{
    Context context;
    boolean isStopped=true;
    public ManaWorker(@NonNull Context context,
                      @NonNull WorkerParameters workerParameters){
        super(context,workerParameters);
        this.context=context;
        isStopped=false;
    }
    @NonNull
    @Override
    public Result doWork() {
        return null;
    }
}
