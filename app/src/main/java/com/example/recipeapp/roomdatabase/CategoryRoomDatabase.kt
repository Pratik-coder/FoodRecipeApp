package com.example.recipeapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.constants.Constant.DATA_BASENAME
import com.example.recipeapp.model.CategoryData


@Database(entities = [CategoryData::class], version = 2, exportSchema = false)
abstract class CategoryRoomDatabase:RoomDatabase()
{
    abstract fun categoryDao():CategoryDao

    companion object
    {
       // const val DATA_BASENAME="categories"
        @Volatile
        private var INSTANCE:CategoryRoomDatabase?=null

        fun getDatabase(context: Context):CategoryRoomDatabase
        {
            val tempInstance= INSTANCE
            if (tempInstance!=null)
            {
                return tempInstance
            }
             synchronized(this)
            {
                val instance=Room.databaseBuilder(context.applicationContext,
                CategoryRoomDatabase::class.java, "CategoryRoomDatabase.db")
                    .addMigrations()
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}