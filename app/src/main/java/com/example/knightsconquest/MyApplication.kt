package com.example.knightsconquest

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            private var activityReferences = 0
            private var isActivityChangingConfigurations = false

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    // App enters foreground
                    resumeMusic()
                }
            }

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    // App enters background
                    pauseMusic()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}

            private fun pauseMusic() {
                val musicServiceIntent = Intent(this@MyApplication, MusicService::class.java).apply {
                    putExtra("action", "pause")
                }
                startService(musicServiceIntent)
            }

            private fun resumeMusic() {
                val musicServiceIntent = Intent(this@MyApplication, MusicService::class.java).apply {
                    putExtra("action", "resume")
                }
                startService(musicServiceIntent)
            }
        })
    }
}
