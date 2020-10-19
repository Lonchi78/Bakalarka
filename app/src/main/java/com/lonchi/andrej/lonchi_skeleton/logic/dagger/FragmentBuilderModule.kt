package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import com.lonchi.andrej.lonchi_skeleton.ui.discover.DiscoverFragment
import com.lonchi.andrej.lonchi_skeleton.ui.fragment.MainFragment
import com.lonchi.andrej.lonchi_skeleton.ui.home.HomeFragment
import com.lonchi.andrej.lonchi_skeleton.ui.meal_planner.MealPlannerFragment
import com.lonchi.andrej.lonchi_skeleton.ui.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [DiscoverModule::class])
    abstract fun contributeDiscoverFragment(): DiscoverFragment

    @ContributesAndroidInjector(modules = [MealPlannerModule::class])
    abstract fun contributeMealPlannerFragment(): MealPlannerFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

}