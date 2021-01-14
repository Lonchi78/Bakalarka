package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import androidx.lifecycle.ViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.fragment.FragmentViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileViewModel
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
abstract class CameraModule {
    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun bindsCameraViewModel(viewModel: CameraViewModel): ViewModel
}

@Module
abstract class LoginModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(viewModel: LoginViewModel): ViewModel
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

@Module
abstract class FoundIngredientsModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoundIngredientsViewModel::class)
    abstract fun bindsFoundIngredientsViewModel(viewModel: FoundIngredientsViewModel): ViewModel
}