package com.lonchi.andrej.lonchi_bakalarka

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.lonchi.andrej.lonchi_bakalarka.logic.app_life.AppLifeTracker
import com.lonchi.andrej.lonchi_bakalarka.logic.dagger.AppComponent
import com.lonchi.andrej.lonchi_bakalarka.logic.dagger.AppModule
import com.lonchi.andrej.lonchi_bakalarka.logic.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
open class LonchiBakalarkaApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var appLifeTracker: AppLifeTracker

    override fun onCreate() {
        super.onCreate()

        //  Setup Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }

        //  Setup Dagger
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .application(this)
                .build()

        appComponent.inject(this)

        //  Setup Application Callbacks
        registerActivityLifecycleCallbacks(appLifeTracker.callbacks)

        //  Vector drawable backward compatibility for API below 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}