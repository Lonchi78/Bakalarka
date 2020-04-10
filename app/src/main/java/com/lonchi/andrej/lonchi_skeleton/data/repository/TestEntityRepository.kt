package com.lonchi.andrej.lonchi_skeleton.data.repository

import com.lonchi.andrej.lonchi_skeleton.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_skeleton.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_skeleton.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_skeleton.data.base.BaseRepository
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