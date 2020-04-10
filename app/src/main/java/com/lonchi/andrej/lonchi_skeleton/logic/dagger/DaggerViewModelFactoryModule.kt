package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */

@Module
abstract class DaggerViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}