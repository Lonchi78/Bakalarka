package com.lonchi.andrej.lonchi_bakalarka.data.utils


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface LanguageStateTracker {
    fun getSystemLanguage(): String

    fun getAppLanguage(): String

    fun refreshSavedLanguage()

    fun hasLanguageChanged(): Boolean = getAppLanguage() != getSystemLanguage()
}
