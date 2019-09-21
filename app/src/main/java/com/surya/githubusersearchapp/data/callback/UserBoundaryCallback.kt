package com.surya.githubusersearchapp.data.callback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.surya.githubusersearchapp.data.local.GithubLocal
import com.surya.githubusersearchapp.data.model.GitUser
import com.surya.githubusersearchapp.data.remote.GithubApiRequest.Companion.searchUsers
import com.surya.githubusersearchapp.data.remote.GithubService

/**
 * Created by suryamudti on 21/09/2019.
 */
class UserBoundaryCallback (private val query: String,
                            private val remote: GithubService,
                            private val local: GithubLocal
) :  PagedList.BoundaryCallback<GitUser>(){

    companion object {
        private const val NETWORK_PAGE_SIZE = 50 // number of items per page to  load form network
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: GitUser) {
        requestAndSaveData(query)
    }

    /**
     * request more items from network with query string
     * local results in db on success
     * or post error message on fail
     */
    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        /*
        make a remote call to github with specified query and increment request page counter
        on success insert the list of user objects into db table and increment last requested page counter
        on fail/error post the error string to livedata
         */
        searchUsers(remote, query, lastRequestedPage, NETWORK_PAGE_SIZE, { users ->
            local.insert(users
            ) { // lambda that executes when insertion is done, moved outside parenthesis
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }
}