package com.lonchi.andrej.lonchi_skeleton.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_skeleton.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_skeleton.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_skeleton.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_skeleton.data.base.BaseRepository
import com.lonchi.andrej.lonchi_skeleton.data.entities.User
import com.lonchi.andrej.lonchi_skeleton.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_skeleton.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_skeleton.data.utils.Resource
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

    fun login(email: String, password: String): Flowable<Resource<User>>
    fun logout(): Completable

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
    override fun hasUserEverBeenLogged(): Boolean = basicSharedPreferences.getAccessTokenFromSharedPreferences().isNotEmpty()

    override fun login(email: String, password: String): Flowable<Resource<User>> =
            api.login(
                    email,
                    password,
                    moshi.adapter(DeviceTracker.DeviceProperties::class.java).toJson(deviceTracker.getDeviceProperties()))
                    .asSyncOperation()
                    .map { it.mapData { it.data.user } }
                    .performSuccessAction {
                        if (it != null) performUserLogin(it) else performUserLogout()
                    }
                    .toFlowable()
                    .startWith(Resource.loading(null))

    override fun logout(): Completable =
            api.logout(basicSharedPreferences.getAccessTokenFromSharedPreferences())
                    .asSyncOperation()
                    .performSuccessAction { performUserLogout() }
                    .performUnauthorizedAction { unauthorizedActionOcurred() }
                    .ignoreElement()

    private fun performUserLogin(it: User) {
        // todo handle saving user data
        // basicPersistence.putAccessTokenToSharedPreferences(it.accessToken)
        db.userDao().saveUser(it)
    }

    private fun performUserLogout() {
        // todo handle removing user data
        // basicPersistence.removeAccessToken()
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
    override fun getAuthToken(): String = basicSharedPreferences.getAccessTokenFromSharedPreferences()

}