package com.surya.githubusersearchapp.data.model


import com.google.gson.annotations.SerializedName
import com.surya.githubusersearchapp.data.model.GitUser

data class GitUserResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var items: List<GitUser?>,
    @SerializedName("total_count")
    var totalCount: Int
)