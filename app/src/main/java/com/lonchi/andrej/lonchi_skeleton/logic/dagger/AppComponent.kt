package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import android.app.Application
import com.lonchi.andrej.lonchi_skeleton.LonchiSkeletonApplication
import com.lonchi.andrej.lonchi_skeleton.ui.main.MainActivity
import com.lonchi.andrej.lonchi_skeleton.ui.profile.ProfileViewModel
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

    fun inject(app: LonchiSkeletonApplication)
    fun inject(mainActivity: MainActivity)
}