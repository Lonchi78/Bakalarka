package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.fragment.MainFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileFragment
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