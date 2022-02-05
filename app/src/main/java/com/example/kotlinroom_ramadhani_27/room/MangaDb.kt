package com.example.kotlinroom_ramadhani_27.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Manga::class],
    version = 1
)
abstract class MangaDb : RoomDatabase(){

    abstract fun  mangaDao() : MangaDao

    companion object{

        @Volatile private var instance :MangaDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MangaDb::class.java,
            "manga12345.db"
        ).build()
    }
}