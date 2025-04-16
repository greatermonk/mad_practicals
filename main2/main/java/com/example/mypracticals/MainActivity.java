package com.example.mypracticals;
import android.app.Application;
import timber.log.Timber;

public class MainActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//         Initialize Timber for logging in debug builds
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        }
    }
}
