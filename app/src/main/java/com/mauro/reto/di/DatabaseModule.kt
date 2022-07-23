package com.mauro.reto.di

import android.app.Application
import com.mauro.reto.storage.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): Database = Database.buildDefault(app)

    @Singleton
    @Provides
    fun provideMovieDao(db: Database): MovieDao = db.movieDao()
}