package com.surya.githubusersearchapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.surya.githubusersearchapp.data.model.GitUser
import com.surya.githubusersearchapp.data.model.UserSearchResult
import com.surya.githubusersearchapp.data.repositories.GithubRepository

/**
 * Created by suryamudti on 21/09/2019.
 */
class MainViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    private val userResult: LiveData<UserSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it) // returns UserSearchResult live data by executing search method from GithubRepository with every update to the queryLiveData
    }


    val users: LiveData<PagedList<GitUser>> = Transformations.switchMap(userResult) { it.data } // returns the list of users live data from UserSearchResult
    val networkErrors: LiveData<String> = Transformations.switchMap(userResult) {
        it.networkErrors // // returns error string live data from UserSearchResult
    }

    /**
     * Search a user based on a query string.
     * post a new value from queryLiveData
     * resulting in initiating a search request from repository manager through userResult
     */
    fun searchUser(queryString: String) {
        queryLiveData.postValue(queryString)
    }



    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value
}