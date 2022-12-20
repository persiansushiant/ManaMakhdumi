package com.mrgamification.manamakhdumi.reciever;

import static com.mrgamification.manamakhdumi.activity.BaseActivity.setAlarmForMe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.mrgamification.manamakhdumi.activity.SplashActivity;
import com.mrgamification.manamakhdumi.model.DaruItem;
import com.mrgamification.manamakhdumi.model.faramushi;

import java.util.List;

public class BootCompleteReciever extends BroadcastReceiver {
    Context myContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        InitiliazeContext(context);
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            StartForeGround();
            CheckDaru();
        }
    }

    private void InitiliazeContext(Context context) {
        myContext= context;
    }

    private void CheckDaru() {
        List<DaruItem> myList = DaruItem.findWithQuery(DaruItem.class,"select * from daru_item");
        for(DaruItem daruItem:myList){
            DoWhateverNeededSoltan(daruItem);
        }


    }

    private void DoWhateverNeededSoltan(DaruItem daruItem) {
        long nextstop=Long.parseLong(daruItem.getNextStop());
        long length=Integer.parseInt(daruItem.getDaruLenght())*1000*60*60;

        while(nextstop<System.currentTimeMillis()){
            nextstop=nextstop+length;
        }
        daruItem.setNextStop(nextstop+"");
        DaruItem.saveInTx(daruItem);
        setAlarmForMe(myContext,daruItem);
    }

    private void StartForeGround() {
        Intent serviceIntent = new Intent(myContext, MyService.class);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R)
            myContext.startForegroundService(serviceIntent);
        else {
            myContext.startService(serviceIntent);
        }
    }
}
