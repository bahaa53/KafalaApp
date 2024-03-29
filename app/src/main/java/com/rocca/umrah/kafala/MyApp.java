package com.rocca.umrah.kafala;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/arabic_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
