package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import android.app.Application
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.bottom_sheet.FoundIngredientsBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.CreateRecipeActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet.AddToMealPlanBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet.RemoveFromMealPlanBottomSheet
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
            FilterModule::class,
            IngredientsListModule::class,
            DiscoverListModule::class,
            MealPlannerModule::class,
            AddToMealPlannerModule::class,
            ProfileModule::class,
            FavouritesModule::class,
            OwnRecipesModule::class,
            AllergensModule::class,
            DietsModule::class,
            SettingsModule::class,
            AboutModule::class,
            RecipeDetailModule::class,
            OnboardingModule::class,
            RecipeDetailCustomModule::class,
            FoundIngredientsModule::class,
            AddToMealPlanModule::class,
            CreateRecipeNameModule::class,
            CreateRecipeTimeModule::class,
            CreateRecipePhotoModule::class,
            CreateRecipeInstructionsModule::class,
            CreateRecipeIngredientsModule::class,
            DiscoverByIngredientsAddModule::class,
            DiscoverByIngredientsResultsModule::class,
            DiscoverByIngredientModule::class,
            DiscoverByIngredientsCameraModule::class,
            CreateRecipeAddInstructionModule::class,
            CreateRecipeAddIngredientModule::class,
            CreateRecipeFinalizeModule::class,
            CreateRecipeAddDietsModule::class,
            CreateRecipeAddNutritionModule::class,
            CreateRecipeAddAllergensModule::class,
            CreateRecipeSuccessModule::class,
            RemoveFromMealPlanModule::class,
            LoginModule::class,
            CreateRecipeModule::class,
            MainFragmentModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: LonchiBakalarkaApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(discoverByIngredientsActivity: DiscoverByIngredientsActivity)
    fun inject(createRecipeActivity: CreateRecipeActivity)

    fun inject(foundIngredientsBottomSheet: FoundIngredientsBottomSheet)
    fun inject(addToMealPlanBottomSheet: AddToMealPlanBottomSheet)
    fun inject(removeFromMealPlanBottomSheet: RemoveFromMealPlanBottomSheet)
}