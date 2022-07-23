package com.mauro.reto

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        Timber.plant(Timber.DebugTree())
        registerActivityLifecycleCallbacks(SplashScreenHelper())
    }
}

class SplashScreenHelper : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        try {
            val activityInfo = activity.packageManager
                .getActivityInfo(activity.componentName, PackageManager.GET_META_DATA)
            val metaData = activityInfo.metaData
            val theme: Int
            if (metaData != null) {
                theme = metaData.getInt("theme", R. style.Theme_Reto)
            } else {
                theme = R.style.Theme_Reto
            }
            activity.setTheme(theme)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}