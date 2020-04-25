package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import com.lonchi.andrej.lonchi_skeleton.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

}