package com.fred.prueba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fred.prueba.models.Leagues


@Database(
    entities = [
        Leagues::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {

    abstract fun dao():Dao

    companion object {
        @Volatile
        private  var instance:AppDatabase?=null;
        private val LOCK = Any();

        operator fun invoke(context:Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{appDatabase->
                instance = appDatabase;
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "pruebaDatabase"
            ).fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2)
                .build();

        private val MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }
    }
}