package com.surya.githubusersearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.surya.githubusersearchapp.data.model.GitUser

/**
 * Created by suryamudti on 21/09/2019.
 */


/**
 * Database schema that holds the list of users.
 */
@Database(
    entities = [GitUser::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDatabase : RoomDatabase(){

    abstract fun usersDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        /**
         * build our room DB
         */
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java, "github_user.db")
                .build()
    }

}