package com.lonchi.andrej.lonchi_bakalarka.logic.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.room.Room
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesImpl
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LanguageStateTracker
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.repository.*
import com.lonchi.andrej.lonchi_bakalarka.logic.app_life.AppLifeTracker
import com.lonchi.andrej.lonchi_bakalarka.logic.app_life.AppLifeTrackerImpl
import com.lonchi.andrej.lonchi_bakalarka.logic.connection.ConnectionObserver
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}-prefs",
                Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    internal fun provideRoomDatabase(): LonchiDatabase {
        return Room.databaseBuilder(
                application,
                LonchiDatabase::class.java,
                "${BuildConfig.APPLICATION_ID}-db")
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDeviceTracker(
        languageStateTracker: LanguageStateTracker,
        persistnterface: SharedPreferencesInterface
    ): DeviceTracker {
        return object : DeviceTracker {
            val TABLET_DP_WIDTH_THRESHOLD = 600

            override fun getDeviceProperties(): DeviceTracker.DeviceProperties {
                return DeviceTracker.DeviceProperties(
                        persistnterface.getFirebaseTokenFromSharedPreferences(),
                        OS_TYPE,
                        Build.VERSION.RELEASE,
                        Build.BRAND,
                        Build.MODEL,
                        if (isTablet(application)) CATEGORY_TABLET else CATEGORY_PHONE,
                        languageStateTracker.getSystemLanguage()
                )
            }

            fun isTablet(context: Context): Boolean {
                val displayMetrics = context.resources.displayMetrics
                return displayMetrics.widthPixels / displayMetrics.density >= TABLET_DP_WIDTH_THRESHOLD
            }
        }
    }

    @Provides
    internal fun provideLanguageTracker(sharedPreferences: SharedPreferencesInterface): LanguageStateTracker {
        return object : LanguageStateTracker {
            override fun getSystemLanguage(): String = application.getString(R.string.system_language)
            override fun getAppLanguage(): String = sharedPreferences.getLastKnownLanguage()
            override fun refreshSavedLanguage() = sharedPreferences.updateLastKnownLanguage(getSystemLanguage())
        }
    }

    @Provides
    @Singleton
    internal fun provideLifeTracker(impl: AppLifeTrackerImpl): AppLifeTracker {
        return impl
    }

    @Provides
    @Singleton
    internal fun provideConnectionObserver(): ConnectionObserver {
        return ConnectionObserver(application)
    }

    /**
     * TODO need to declare providing methods if you implemented any repository
     * This is where place where all repository-providing methods will be located
     */

    @Provides
    @Singleton
    internal fun provideUserRepository(impl: UserRepositoryImpl): UserRepository {
        return impl
    }

    @Provides
    @Singleton
    internal fun provideImageLabelingRepository(impl: ImageLabelingRepositoryImpl): ImageLabelingRepository {
        return impl
    }

    @Provides
    @Singleton
    internal fun provideRecipesRepository(impl: RecipesRepositoryImpl): RecipesRepository {
        return impl
    }
}

@Module
class PersistenceModule {
    @Provides
    internal fun providePersistenceInterface(impl: SharedPreferencesImpl): SharedPreferencesInterface {
        return impl
    }
}

@Module
class RestModule {

    companion object {
        private const val READ_TIMEOUT = 300L
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_LATITUDE = "latitude"
        private const val HEADER_LONGITUDE = "longitude"
        private const val HEADER_ACCEPT_VALUE = "application/json"
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val HEADER_CONTENT_TYPE_VALUE = "application/json"
        private const val HEADER_LANGUAGE = "lang"
    }

    @Provides
    internal fun provideOkHttpClient(
        preferences: SharedPreferences,
        persist: SharedPreferencesInterface,
        languageStateTracker: LanguageStateTracker
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        builder.addInterceptor { chain ->
            chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
                            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
                            .addHeader(HEADER_LANGUAGE, languageStateTracker.getSystemLanguage())
                            .build()
            )
        }
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    internal fun provideWebApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)
}