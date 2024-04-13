package ru.ermolnik.filmapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ermolnik.filmapp.data.local.dao.MoviesDao
import ru.ermolnik.filmapp.data.local.dao.RemoteKeysDao
import ru.ermolnik.filmapp.data.local.entity.MovieEntity
import ru.ermolnik.filmapp.data.local.entity.RemoteKeysEntity

@Database(entities = [MovieEntity::class, RemoteKeysEntity::class], version = 1, exportSchema = false)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDb? = null

        fun getDatabase(context: Context): MoviesDb {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDb::class.java,
                        "moviesDb"
                    )
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
