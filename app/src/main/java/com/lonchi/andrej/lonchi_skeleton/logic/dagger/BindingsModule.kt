package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import androidx.lifecycle.ViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.discover.DiscoverViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.fragment.FragmentViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.home.HomeViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.main.MainViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.meal_planner.MealPlannerViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */

@Module
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}

@Module
abstract class MainFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(FragmentViewModel::class)
    abstract fun bindsFragmentViewModel(viewModel: FragmentViewModel): ViewModel
}

@Module
abstract class HomeModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel
}

@Module
abstract class DiscoverModule {
    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class)
    abstract fun bindsDiscoverViewModel(viewModel: DiscoverViewModel): ViewModel
}

@Module
abstract class MealPlannerModule {
    @Binds
    @IntoMap
    @ViewModelKey(MealPlannerViewModel::class)
    abstract fun bindsMealPlannerViewModel(viewModel: MealPlannerViewModel): ViewModel
}

@Module
abstract class ProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindsProfileViewModel(viewModel: ProfileViewModel): ViewModel
}
