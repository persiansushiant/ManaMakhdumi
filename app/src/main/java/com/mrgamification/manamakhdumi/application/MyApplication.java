package com.mrgamification.manamakhdumi.application;

import com.mrgamification.manamakhdumi.R;
import com.orm.SugarApp;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
@ReportsCrashes(
        mailTo = "hatami.kamran@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.app_name)

public class MyApplication extends SugarApp {
    public static MyApplication instance;
    public static int delay=10;

    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        instance=this;

        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/lalezar.ttf").setFontAttrId( R.attr.fontPath).build())).build());

    }

    public MyApplication() {
        instance = this;
    }
}
