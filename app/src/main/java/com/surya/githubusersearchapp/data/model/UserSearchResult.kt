package com.surya.githubusersearchapp.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created by suryamudti on 21/09/2019.
 */
data class UserSearchResult(
    val data: LiveData<PagedList<GitUser>>,
    val networkErrors: LiveData<String>
)