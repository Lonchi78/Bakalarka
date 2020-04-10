package com.lonchi.andrej.lonchi_skeleton.logic.app_life

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_skeleton.data.utils.conditionalDebounce
import com.lonchi.andrej.lonchi_skeleton.data.utils.getDistinct
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AppLifeTrackerImpl @Inject constructor() : Application.ActivityLifecycleCallbacks,
    AppLifeTracker {
    companion object {
        private const val TIMBER_TAG = "APP LIFE TRACKER: %s"
    }

    private var activitiesRunning: Int = 0
    private val appInForegroundLocal = MutableLiveData<Boolean>()
    override val appInForeground = appInForegroundLocal.conditionalDebounce(2000, { !it }).getDistinct()
    override val callbacks: Application.ActivityLifecycleCallbacks get() = this

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        if (activitiesRunning == 0) {
            Timber.d(TIMBER_TAG, "going in foreground")
            appInForegroundLocal.postValue(true)
        }
        activitiesRunning++
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        activitiesRunning--
        if (activitiesRunning == 0) {
            Timber.d(TIMBER_TAG, "going in background")
            appInForegroundLocal.postValue(false)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity) {}
}