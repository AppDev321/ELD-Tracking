package com.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.core.database.convertors.DateConvertors
import com.core.database.dao.DictionaryDao
import com.core.database.entity.DictionaryEntity

@Database(
    entities = [DictionaryEntity::class],
    version = DatabaseMigrations.DB_VERSION,
    exportSchema = false
)
@TypeConverters(DateConvertors::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dictionaryDao(): DictionaryDao


    companion object {

        private const val dbName = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            dbName
                        )
                            //.createFromAsset("dictionary.db")
                            .addMigrations(*DatabaseMigrations.MIGRATIONS).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}