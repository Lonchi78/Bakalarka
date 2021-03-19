package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.mapToUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import retrofit2.Retrofit
import timber.log.Timber
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

    fun getUserData()
    fun updateUserData()
    fun updateDiets()
    fun updateIntolerances()
    fun updateCustomRecipes()
    fun updateFavouriteRecipes()
}

class UserRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker
) : BaseRepository(db, api, prefs, retrofit), UserRepository {

    companion object {
        private const val DB_KEY_USERS = "users"
        private const val DB_USERDATA_NAME = "name"
        private const val DB_USERDATA_EMAIL = "email"
        private const val DB_USERDATA_DIETS = "diets"
        private const val DB_USERDATA_INTOLERANCES = "intolerances"
        private const val DB_USERDATA_FAVOURITE_RECIPES = "favouriteRecipes"
        private const val DB_USERDATA_CUSTOM_RECIPES = "customRecipes"
    }

    private var disposable: CompositeDisposable? = null

    private var database: DatabaseReference = Firebase.database.reference

    init {
        disposable = CompositeDisposable()
    }

    /**
     * Get logged user state based on data in database
     * If user is not in db than user is not logged and Resource contains error
     * If user is in db Resource is success with user data
     */
    override val loggedUser: LiveData<Resource<User>> = Transformations.map(db.userDao().listAll()) {
        it.firstOrNull()?.let { Resource.success(it) }
                ?: Resource.error(ErrorIdentification.Authentication(), null)
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
        db.favouriteRecipesDao().deleteAllRecipes()
        db.customRecipesDao().deleteAllRecipes()
        db.dietsDao().deleteAll()
        db.intolerancesDao().deleteAll()
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

    override fun getUserData() {

    }

    override fun updateUserData() {
        disposable?.dispose()
        disposable?.add(
            Single.zip(
                db.userDao().listAllSingle(),
                db.customRecipesDao().getAllRecipesSingle(),
                db.favouriteRecipesDao().getAllRecipesSingle(),
                db.dietsDao().listAllSingle(),
                db.intolerancesDao().listAllSingle(),
                { user, customRecipes, favouriteRecipes, diets, intolerances ->
                    UserData(
                        name = user.firstOrNull()?.name ?: "",
                        email = user.firstOrNull()?.email ?: "",
                        diets = diets.firstOrNull() ?: Diets(),
                        intolerances = intolerances.firstOrNull() ?: Intolerances(),
                        customRecipes = customRecipes,
                        favouriteRecipes = favouriteRecipes
                    )
                }
            )
                .subscribe({
                    val userUid = prefs.getUserUidFromSharedPreferences()
                    database.child(DB_KEY_USERS).child(userUid).setValue(it)
                }, {
                    Timber.e("updateUserData err: $it")
                })
        )
    }

    override fun updateDiets() {
        disposable?.dispose()
        disposable?.add(
            db.dietsDao().listAllSingle()
                .subscribe({
                    val userUid = prefs.getUserUidFromSharedPreferences()
                    database
                        .child(DB_KEY_USERS)
                        .child(userUid)
                        .child(DB_USERDATA_DIETS)
                        .setValue(it.firstOrNull() ?: Diets())
                }, {
                    Timber.e("updateDiets err: $it")
                })
        )
    }

    override fun updateIntolerances() {
        disposable?.dispose()
        disposable?.add(
            db.intolerancesDao().listAllSingle()
                .subscribe({
                    val userUid = prefs.getUserUidFromSharedPreferences()
                    database
                        .child(DB_KEY_USERS)
                        .child(userUid)
                        .child(DB_USERDATA_INTOLERANCES)
                        .setValue(it.firstOrNull() ?: Intolerances())
                }, {
                    Timber.e("updateIntolerances err: $it")
                })
        )
    }

    override fun updateCustomRecipes() {
        disposable?.dispose()
        disposable?.add(
            db.customRecipesDao().getAllRecipesSingle()
                .subscribe({
                    val userUid = prefs.getUserUidFromSharedPreferences()
                    database
                        .child(DB_KEY_USERS)
                        .child(userUid)
                        .child(DB_USERDATA_CUSTOM_RECIPES)
                        .setValue(it)
                }, {
                    Timber.e("updateCustomRecipes err: $it")
                })
        )
    }

    override fun updateFavouriteRecipes() {
        disposable?.dispose()
        disposable?.add(
            db.favouriteRecipesDao().getAllRecipesSingle()
                .subscribe({
                    val userUid = prefs.getUserUidFromSharedPreferences()
                    database
                        .child(DB_KEY_USERS)
                        .child(userUid)
                        .child(DB_USERDATA_FAVOURITE_RECIPES)
                        .setValue(it)
                }, {
                    Timber.e("updateFavouriteRecipes err: $it")
                })
        )
    }
}