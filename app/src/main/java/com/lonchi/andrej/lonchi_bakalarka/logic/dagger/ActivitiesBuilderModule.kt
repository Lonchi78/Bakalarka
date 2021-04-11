package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.CreateRecipeActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [CameraModule::class])
    abstract fun bindCameraActivity(): CameraActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [DiscoverByIngredientModule::class])
    abstract fun bindDiscoverByIngredientActivity(): DiscoverByIngredientsActivity

    @ContributesAndroidInjector(modules = [CreateRecipeModule::class])
    abstract fun bindCreateRecipeActivity(): CreateRecipeActivity

}