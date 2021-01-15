package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseUser
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.mapToUser
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface UserRepository {
    /**
     * Primary livedata stream containing logged user state
     */
    val loggedUser: LiveData<Resource<User>>

    /**
     * Method used to reduce lag for first app start.
     * May not be necessary if user does not need verification on server.
     */
    fun hasUserEverBeenLogged(): Boolean

    fun performUserLogin(firebaseUser: FirebaseUser)
    fun performUserLogout()

    fun unauthorizedActionOcurred()
    fun getAuthToken(): String
}

class UserRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker
) : BaseRepository(db, api, prefs, retrofit), UserRepository {

    /**
     * Get logged user state based on data in database
     * If user is not in db than user is not logged and Resource contains error
     * If user is in db Resource is success with user data
     */
    override val loggedUser: LiveData<Resource<User>> = Transformations.map(db.userDao().listAll()) {
        it.firstOrNull()?.let { Resource.success(it) }
                ?: Resource.error(ErrorIdentification.UserNotFoundOnDevice(), null)
    }


    /**
     * Get user logged state based on stored access token in shared preferences
     */
    override fun hasUserEverBeenLogged(): Boolean = basicSharedPreferences.getUserUidFromSharedPreferences().isNotEmpty()

    override fun performUserLogin(firebaseUser: FirebaseUser) {
        prefs.putUserUidToSharedPreferences(firebaseUser.uid)
        db.userDao().saveUser(firebaseUser.mapToUser())
    }

    override fun performUserLogout() {
        prefs.removeUserId()
        db.userDao().deleteAll()
    }

    /**
     * Calling this method will remove user from database, which will cause loggedUser to trigger with unauthorized.
     * This may not be exactly what you need in project, depending on graphics...
     */
    override fun unauthorizedActionOcurred() {
        performUserLogout()
    }

    /**
     * Get access token from shared preferences
     * @return access token
     */
    override fun getAuthToken(): String = basicSharedPreferences.getUserUidFromSharedPreferences()

}