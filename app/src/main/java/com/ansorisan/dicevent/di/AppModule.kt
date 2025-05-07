package com.ansorisan.dicevent.di

import android.content.Context
import androidx.room.Room
import com.ansorisan.dicevent.BuildConfig
import com.ansorisan.dicevent.base.data.api.ApiService
import com.ansorisan.dicevent.base.data.repositories.EventRepositoryImpl
import com.ansorisan.dicevent.features.events.favorite.data.repository.EventRepositoryImpl as FavEventRepoImpl
import com.ansorisan.dicevent.base.db.EventDatabase
import com.ansorisan.dicevent.base.domain.repositories.Event
import com.ansorisan.dicevent.features.events.favorite.data.data_source.EventDao
import com.ansorisan.dicevent.features.events.favorite.domain.repository.Event as FavEventRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor =
            if(BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) else HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.NONE)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideEventApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideEventRepository(api: ApiService): Event = EventRepositoryImpl(api)

    // Room Database
    @Provides
    @Singleton
    fun provideEventDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: EventDatabase): EventDao = db.eventDao()

    @Provides
    @Singleton
    fun provideNoteRepository(dao: EventDao): FavEventRepo = FavEventRepoImpl(dao)
}