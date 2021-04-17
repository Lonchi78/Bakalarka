package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.AllergensFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize.*
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients.CreateRecipeAddIngredientFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients.CreateRecipeIngredientsFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions.CreateRecipeAddInstructionFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions.CreateRecipeInstructionsFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.name.CreateRecipeNameFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.photo.CreateRecipePhotoFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.time.CreateRecipeTimeFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.DietsFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.FilterFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byQuery.DiscoverByQueryFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsListFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsResultsFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsAddFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.favourites.FavouritesFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.fragment.MainFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.onboarding.OnboardingFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.ownRecipes.OwnRecipesFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.custom.RecipeDetailCustomFragment
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

    @ContributesAndroidInjector(modules = [FilterModule::class])
    abstract fun contributeFilterFragment(): FilterFragment

    @ContributesAndroidInjector(modules = [IngredientsListModule::class])
    abstract fun contributeIngredientsListFragment(): DiscoverByIngredientsListFragment

    @ContributesAndroidInjector(modules = [DiscoverByIngredientsResultsModule::class])
    abstract fun contributeDiscoverByIngredientsResultsFragment(): DiscoverByIngredientsResultsFragment

    @ContributesAndroidInjector(modules = [DiscoverListModule::class])
    abstract fun contributeDiscoverListFragment(): DiscoverByQueryFragment

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

    @ContributesAndroidInjector(modules = [RecipeDetailModule::class])
    abstract fun contributeRecipeDetailFragment(): RecipeDetailFragment

    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    abstract fun contributeOnboardingFragment(): OnboardingFragment

    @ContributesAndroidInjector(modules = [RecipeDetailCustomModule::class])
    abstract fun contributeRecipeDetailCustomFragment(): RecipeDetailCustomFragment

    @ContributesAndroidInjector(modules = [FoundIngredientsModule::class])
    abstract fun contributeFoundIngredientsBottomSheet(): FoundIngredientsBottomSheet

    @ContributesAndroidInjector(modules = [CreateRecipeNameModule::class])
    abstract fun contributeCreateRecipeNameFragment(): CreateRecipeNameFragment

    @ContributesAndroidInjector(modules = [CreateRecipeTimeModule::class])
    abstract fun contributeCreateRecipeTimeFragment(): CreateRecipeTimeFragment

    @ContributesAndroidInjector(modules = [CreateRecipeIngredientsModule::class])
    abstract fun contributeCreateRecipeIngredientsFragment(): CreateRecipeIngredientsFragment

    @ContributesAndroidInjector(modules = [DiscoverByIngredientsAddModule::class])
    abstract fun contributeDiscoverByIngredientsAddFragment(): DiscoverByIngredientsAddFragment

    @ContributesAndroidInjector(modules = [CreateRecipeAddIngredientModule::class])
    abstract fun contributeCreateRecipeAddIngredientFragment(): CreateRecipeAddIngredientFragment

    @ContributesAndroidInjector(modules = [CreateRecipeInstructionsModule::class])
    abstract fun contributeCreateRecipeInstructionsFragment(): CreateRecipeInstructionsFragment

    @ContributesAndroidInjector(modules = [CreateRecipeAddInstructionModule::class])
    abstract fun contributeCreateRecipeAddInstructionFragment(): CreateRecipeAddInstructionFragment

    @ContributesAndroidInjector(modules = [CreateRecipePhotoModule::class])
    abstract fun contributeCreateRecipePhotoFragment(): CreateRecipePhotoFragment

    @ContributesAndroidInjector(modules = [CreateRecipeFinalizeModule::class])
    abstract fun contributeCreateRecipeFinalizeFragment(): CreateRecipeFinalizeFragment

    @ContributesAndroidInjector(modules = [CreateRecipeAddDietsModule::class])
    abstract fun contributeCreateRecipeAddDietsFragment(): CreateRecipeAddDietsFragment

    @ContributesAndroidInjector(modules = [CreateRecipeAddNutritionModule::class])
    abstract fun contributeCreateRecipeAddNutritionFragment(): CreateRecipeAddNutritionFragment

    @ContributesAndroidInjector(modules = [CreateRecipeAddAllergensModule::class])
    abstract fun contributeCreateRecipeAddAllergensFragment(): CreateRecipeAddAllergensFragment

    @ContributesAndroidInjector(modules = [CreateRecipeSuccessModule::class])
    abstract fun contributeCreateRecipeSuccessFragment(): CreateRecipeSuccessFragment

}