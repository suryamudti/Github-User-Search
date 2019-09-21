package com.surya.githubusersearchapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surya.githubusersearchapp.data.repositories.GithubRepository

/**
 * Created by suryamudti on 21/09/2019.
 */
class MainViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
}