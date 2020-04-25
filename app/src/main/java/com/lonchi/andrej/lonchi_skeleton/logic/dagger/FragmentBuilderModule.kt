package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import com.lonchi.andrej.lonchi_skeleton.ui.fragment.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment

}