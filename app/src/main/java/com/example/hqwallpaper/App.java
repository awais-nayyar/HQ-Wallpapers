package com.example.hqwallpaper;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;

public class App extends MultiDexApplication {

    private static final String ONESIGNAL_APP_ID = "e2a10ddf-4b51-4f92-bd04-404f5882832f";
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }
}
