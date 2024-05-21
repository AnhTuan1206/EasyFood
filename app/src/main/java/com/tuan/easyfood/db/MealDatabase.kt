package com.tuan.easyfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tuan.easyfood.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
    companion object{
        @Volatile
        private var INSTANCE: MealDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE?: synchronized(LOCK){
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context,
                MealDatabase::class.java,
                "meal_db.db").build()

    }
}