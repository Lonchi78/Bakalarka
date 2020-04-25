package com.lonchi.andrej.lonchi_skeleton.logic.app_life

import android.app.Application
import androidx.lifecycle.LiveData


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface AppLifeTracker {
    val appInForeground: LiveData<Boolean>
    val callbacks: Application.ActivityLifecycleCallbacks
}