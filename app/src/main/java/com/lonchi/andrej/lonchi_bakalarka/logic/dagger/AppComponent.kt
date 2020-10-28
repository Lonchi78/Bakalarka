package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import android.app.Application
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */

@Singleton
@Component(
        modules = [AndroidInjectionModule::class,
            ActivitiesBuilderModule::class,
            FragmentBuilderModule::class,
            ServiceBuilderModule::class,
            AppModule::class,
            DaggerViewModelFactoryModule::class,
            PersistenceModule::class,
            RestModule::class,
            MainModule::class,
            CameraModule::class,
            HomeModule::class,
            DiscoverModule::class,
            MealPlannerModule::class,
            ProfileModule::class,
            MainFragmentModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: LonchiBakalarkaApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(cameraActivity: CameraActivity)
}