package com.surya.githubusersearchapp.data.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.surya.githubusersearchapp.data.model.GitUser

/**
 * Created by suryamudti on 21/09/2019.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<GitUser>)

    // Do a similar query as the search API:
    // Look for users that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM users WHERE (login LIKE :queryString)")
    fun usersByName(queryString: String): DataSource.Factory<Int, GitUser>
}