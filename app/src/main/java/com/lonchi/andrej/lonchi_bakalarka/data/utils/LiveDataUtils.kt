package com.lonchi.andrej.lonchi_bakalarka.data.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
fun <A, B> combineLatestLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = Pair(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }
}

fun <A, B, C> combineLatestLiveData(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>): LiveData<Triple<A, B, C>> {
    return MediatorLiveData<Triple<A, B, C>>().apply {
        var lastA: A? = null
        var lastB: B? = null
        var lastC: C? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            val localLastC = lastC
            if (localLastA != null && localLastB != null && localLastC != null)
                this.value = Triple(localLastA, localLastB, localLastC)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
        addSource(c) {
            lastC = it
            update()
        }
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

fun <A, B, C, D> combineLatestLiveData(
        a: LiveData<A>,
        b: LiveData<B>,
        c: LiveData<C>,
        d: LiveData<D>): LiveData<Quadruple<A, B, C, D>> {
    return MediatorLiveData<Quadruple<A, B, C, D>>().apply {
        var lastA: A? = null
        var lastB: B? = null
        var lastC: C? = null
        var lastD: D? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            val localLastC = lastC
            val localLastD = lastD

            if (localLastA != null && localLastB != null && localLastC != null && localLastD != null)
                this.value = Quadruple(localLastA, localLastB, localLastC, localLastD)
        }
        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
        addSource(c) {
            lastC = it
            update()
        }
        addSource(d) {
            lastD = it
            update()
        }
    }
}

fun <A> lateLoadLiveData(
    a: LiveData<A>,
    b: LiveData<Resource<A>>,
    combine: (a: A?, b: A?) -> A?): LiveData<Resource<A>> =
        Transformations.map(combineLatestLiveData(a, b)) {
            return@map Resource(
                    it.second.status,
                    combine(it.first, it.second.data),
                    it.second.errorIdentification)
        }

/**
 * Mutable live data that perform onActive on becoming active and onInactive on becoming inactive
 */
open class ActiveMutableLiveData<A>(
        protected val onActive: (() -> Unit)? = null,
        protected val onInactive: (() -> Unit)? = null) : MutableLiveData<A>() {

    override fun onActive() {
        super.onActive()
        onActive?.invoke()
    }

    override fun onInactive() {
        onInactive?.invoke()
        super.onInactive()
    }
}

open class TriggerLiveData<A>(
        protected val triggerOnActive: Boolean = true,
        protected val triggerOnActiveWithValue: ((A?) -> Boolean) = { true },
        protected val disposeOnInactive: Boolean = false,
        baseValue: A? = null,
        protected var obtainer: () -> Observable<A>) : LiveData<A>() {
    init {
        value = baseValue
    }

    var disposable: Disposable? = null

    fun trigger() {
        try {
            disposable?.dispose()
        } catch (e: Exception) {
        }
        disposable = obtainer().subscribe(
                { next -> postValue(next) },
                { Timber.e(it) },
                { disposable = null })
    }

    override fun onActive() {
        super.onActive()
        if (triggerOnActive || triggerOnActiveWithValue(value)) {
            trigger()
        }
    }

    override fun onInactive() {
        if (disposeOnInactive) {
            try {
                disposable?.dispose()
                disposable = null
            } catch (e: Exception) {
                //no-op, expecting invalid state on already finished,...
            }
        }
        super.onInactive()
    }
}

class OpenTriggerLiveData<A>(triggerOnActive: Boolean = true,
                             triggerOnActiveWithValue: ((A?) -> Boolean) = { true },
                             disposeOnInactive: Boolean = false,
                             baseValue: A? = null,
                             obtainer: () -> Observable<A>) : TriggerLiveData<A>(triggerOnActive, triggerOnActiveWithValue, disposeOnInactive, baseValue, obtainer) {
    fun overrideValue(it: A) = postValue(it)
}

class ParametrizedTriggerLiveData<B, A>(
        val disposeOnInactive: Boolean = false,
        baseValue: A? = null,
        val obtainer: (parameter: B) -> Observable<A>) : LiveData<A>() {
    init {
        value = baseValue
    }

    var disposables = mutableListOf<Disposable>()

    fun trigger(parameter: B) {
        Timber.d("triggered with parameter $parameter")
        clearDisposables()
        disposables.add(obtainer(parameter).subscribe { next -> postValue(next) })
    }

    private fun clearDisposables() {
        disposables.forEach {
            try {
                it.dispose()
            } catch (e: Exception) {
            }
        }
        disposables.clear()
    }

    override fun onInactive() {
        if (disposeOnInactive) {
            clearDisposables()
        }
        super.onInactive()
    }
}

fun <A, B> DependentLiveData(a: LiveData<A>, b: ParametrizedTriggerLiveData<A, B>): LiveData<B> {
    return MediatorLiveData<B>().apply {
        addSource(a) {
            if (it != null) b.trigger(it)
        }
        addSource(b) {
            this.value = it
        }
    }
}

fun <A> LastValueLiveData(vararg source: LiveData<A>): LiveData<A> =
        MediatorLiveData<A>().apply {
            source.forEach { addSource(it) { this.value = it } }

        }

class ChangingMapperLiveData<A, B>(source: LiveData<A>, private var mapper: (it: A) -> B) : MediatorLiveData<B>() {
    private var lastOriginal: A? = null

    init {
        addSource(source) {
            lastOriginal = it
            update()
        }
    }

    private fun update() {
        val localOriginal = lastOriginal
        if (localOriginal != null) this.value = mapper.invoke(localOriginal)
    }

    fun changeMapper(mapper: (it: A) -> B) {
        this.mapper = mapper
        update()
    }
}

class InactiveInvalidatedLiveData<A> : MutableLiveData<A>() {
    override fun postValue(value: A) {
        if (hasActiveObservers()) super.postValue(value)
    }

    override fun onActive() {
        this.value = null
        super.onActive()
    }

    override fun onInactive() {
        this.value = null
        super.onInactive()
    }
}

fun <T> LiveData<T>.debounce(debounce: Long = 1000L) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, debounce)
    }
}

fun <T> LiveData<T>.conditionalDebounce(debounce: Long = 1000L, debounceItem: (T) -> Boolean) = MediatorLiveData<T>().also { mld ->
    val source = this
    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        mld.value = source.value
    }

    mld.addSource(source) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, if (debounceItem(it)) debounce else 0)
    }
}


fun <T> LiveData<T>.getDistinctBy(isDistinct: (T?, T?) -> Boolean): LiveData<T> =
        MediatorLiveData<T>().also { mld ->
            mld.addSource(this, object : Observer<T> {
                private var initialized = false
                private var lastObj: T? = null

                override fun onChanged(obj: T?) {
                    if (!initialized) {
                        initialized = true
                        lastObj = obj
                        mld.postValue(lastObj)
                    } else if ((obj == null && lastObj != null) || isDistinct.invoke(obj, lastObj)) {
                        lastObj = obj
                        mld.postValue(lastObj)
                    }
                }
            })
        }


fun <T> LiveData<T>.getDistinct(): LiveData<T> = this.getDistinctBy { a, b -> a != b }

open class FilteredLiveData<A, B>(protected val defaultFilterValue: A,
                                  source: LiveData<B>,
                                  protected val filter: ((a: A, b: B) -> B),
                                  protected val isEqual: ((a: A?, b: A?) -> Boolean) = { a, b -> a == b }) {
    data class FilterResult<A, B>(val currentFilter: A, val items: B) {
        fun <C> mapData(mapper: (it: B) -> C) = FilterResult(currentFilter, mapper.invoke(items))
    }

    protected val currentQuery = MutableLiveData<A>().apply { postValue(defaultFilterValue) }

    val output = Transformations.map(combineLatestLiveData(currentQuery.getDistinct(), source)) { FilterResult(it.first, filter(it.first, it.second)) }

    fun updateFilter(newFilter: A?) = currentQuery.postValue(newFilter ?: defaultFilterValue)
    fun initFilter() = updateFilter(null)
    fun isFilterApplied() = !isEqual(currentQuery.value, defaultFilterValue)
}