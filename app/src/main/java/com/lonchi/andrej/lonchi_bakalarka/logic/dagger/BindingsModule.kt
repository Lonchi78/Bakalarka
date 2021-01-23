package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import androidx.lifecycle.ViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.about.AboutViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.AllergensViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.DietsViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.favourites.FavouritesViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.fragment.FragmentViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.ownRecipes.OwnRecipesViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeDetailViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.settings.SettingsViewModel
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
abstract class FavouritesModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindsFavouritesViewModel(viewModel: FavouritesViewModel): ViewModel
}

@Module
abstract class OwnRecipesModule {
    @Binds
    @IntoMap
    @ViewModelKey(OwnRecipesViewModel::class)
    abstract fun bindsOwnRecipesViewModel(viewModel: OwnRecipesViewModel): ViewModel
}

@Module
abstract class AllergensModule {
    @Binds
    @IntoMap
    @ViewModelKey(AllergensViewModel::class)
    abstract fun bindsAllergensViewModel(viewModel: AllergensViewModel): ViewModel
}

@Module
abstract class DietsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DietsViewModel::class)
    abstract fun bindsDietsViewModel(viewModel: DietsViewModel): ViewModel
}

@Module
abstract class SettingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindsSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}

@Module
abstract class AboutModule {
    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindsAboutViewModel(viewModel: AboutViewModel): ViewModel
}

@Module
abstract class RecipeDetailModule {
    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailViewModel::class)
    abstract fun bindsRecipeDetailViewModel(viewModel: RecipeDetailViewModel): ViewModel
}

@Module
abstract class FoundIngredientsModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoundIngredientsViewModel::class)
    abstract fun bindsFoundIngredientsViewModel(viewModel: FoundIngredientsViewModel): ViewModel
}