package com.lonchi.andrej.lonchi_skeleton.logic.dagger

import androidx.lifecycle.ViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.fragment.FragmentViewModel
import com.lonchi.andrej.lonchi_skeleton.ui.main.MainViewModel
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
