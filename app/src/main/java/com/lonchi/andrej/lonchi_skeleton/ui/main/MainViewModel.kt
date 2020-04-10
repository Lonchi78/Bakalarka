package com.lonchi.andrej.lonchi_skeleton.ui.main

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_skeleton.data.repository.UserRepository
import com.lonchi.andrej.lonchi_skeleton.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val state: MutableLiveData<StateMainModule> = MutableLiveData()

    fun resetState() {
        state.postValue(StateMainModule())
    }

    class StateMainModule(
        val showState: Boolean = false,
        val data: Any? = null
    )
}
