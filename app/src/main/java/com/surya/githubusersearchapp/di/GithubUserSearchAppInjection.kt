package com.surya.githubusersearchapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.surya.githubusersearchapp.data.local.GithubLocal
import com.surya.githubusersearchapp.data.local.UsersDatabase
import com.surya.githubusersearchapp.data.remote.GithubService
import com.surya.githubusersearchapp.data.repositories.GithubRepository
import com.surya.githubusersearchapp.ui.MainViewModelFactory
import java.util.concurrent.Executors

/**
 * Created by suryamudti on 21/09/2019.
 */

object GithubUserSearchAppInjection{
    /**
     * Creates an instance of [GithubLocal] based on the database DAO.
     */
    private fun provideCache(context: Context): GithubLocal {
        val database = UsersDatabase.getInstance(context) // create Room db
        return GithubLocal(database.usersDao(),// Dao implementation
            Executors.newSingleThreadExecutor()// single thread sequential executor
        )
    }

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocal]
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(
            GithubService.create(),//create retrofit service call for github with implementation of endpoints
            provideCache(context)// set cache
        )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     *
     * has instance of the GithubRepository in constructor and context
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return MainViewModelFactory(provideGithubRepository(context))
    }
}