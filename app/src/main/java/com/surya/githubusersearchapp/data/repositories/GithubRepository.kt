package com.surya.githubusersearchapp.data.repositories

import com.surya.githubusersearchapp.data.local.GithubLocal
import com.surya.githubusersearchapp.data.remote.GithubService

/**
 * Created by suryamudti on 21/09/2019.
 */
class GithubRepository(
    private val remote : GithubService,
    private val local : GithubLocal

) {

    /**
     * Search users whose names match the query.
     */
    fun search(query: String){

    }

}