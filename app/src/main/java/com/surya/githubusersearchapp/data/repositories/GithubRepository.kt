package com.surya.githubusersearchapp.data.repositories

import com.surya.githubusersearchapp.data.remote.GithubService

/**
 * Created by suryamudti on 21/09/2019.
 */
class GithubRepository(
    private val service : GithubService
) {

    /**
     * Search users whose names match the query.
     */
    fun search(query: String){

    }

}