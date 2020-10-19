package com.lonchi.andrej.lonchi_bakalarka.data.repository

import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Singleton
class TestEntityRepository @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val userRepository: UserRepository
) : BaseRepository(db, api, prefs, retrofit) {}