package com.mauro.reto.storage

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mauro.reto.core.storage.Converters
import com.mauro.reto.storage.entity.*

@Database(
    entities = [
        MovieDb::class,
    ],
    version = 8
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private const val databaseName = "movie-db"

        fun buildDefault(context: Context) =
                Room.databaseBuilder(context, com.mauro.reto.storage.Database::class.java, databaseName)
                    .fallbackToDestructiveMigration()
                    .build()

        @VisibleForTesting
        fun buildTest(context: Context) =
                Room.inMemoryDatabaseBuilder(context, com.mauro.reto.storage.Database::class.java)
                    .build()
    }
}