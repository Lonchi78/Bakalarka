package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import com.lonchi.andrej.lonchi_bakalarka.ui.about.AboutFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.AllergensFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.DietsFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverListFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.favourites.FavouritesFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.fragment.MainFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.ownRecipes.OwnRecipesFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeDetailFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.settings.SettingsFragment
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

    @ContributesAndroidInjector(modules = [DiscoverListModule::class])
    abstract fun contributeDiscoverListFragment(): DiscoverListFragment

    @ContributesAndroidInjector(modules = [MealPlannerModule::class])
    abstract fun contributeMealPlannerFragment(): MealPlannerFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [FavouritesModule::class])
    abstract fun contributeFavouritesFragment(): FavouritesFragment

    @ContributesAndroidInjector(modules = [OwnRecipesModule::class])
    abstract fun contributeOwnRecipesFragment(): OwnRecipesFragment

    @ContributesAndroidInjector(modules = [AllergensModule::class])
    abstract fun contributeAllergensFragment(): AllergensFragment

    @ContributesAndroidInjector(modules = [DietsModule::class])
    abstract fun contributeDietsFragment(): DietsFragment

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [AboutModule::class])
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector(modules = [RecipeDetailModule::class])
    abstract fun contributeRecipeDetailFragment(): RecipeDetailFragment

    @ContributesAndroidInjector(modules = [FoundIngredientsModule::class])
    abstract fun contributeFoundIngredientsBottomSheet(): FoundIngredientsBottomSheet
}