package com.surya.githubusersearchapp.data.remote

import android.util.Log
import com.surya.githubusersearchapp.data.model.GitUser
import com.surya.githubusersearchapp.data.model.GitUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by suryamudti on 21/09/2019.
 */

private const val TAG = "GithubService"
private const val IN_QUALIFIER = "in:name,description"

class GithubApiRequest {

    companion object{
        fun searchUsers(
            service: GithubService,
            query: String,
            page: Int,
            itemsPerPage: Int,
            onSuccess: (repos: List<GitUser>) -> Unit,
            onError: (error: String) -> Unit) {


            val apiQuery = query + IN_QUALIFIER

            // make a service call
            service.searchUsers(apiQuery, page, itemsPerPage).enqueue(
                object : Callback<GitUserResponse> {
                    override fun onFailure(call: Call<GitUserResponse>?, t: Throwable) {
                        Log.d(TAG, "fail to get data")
                        onError(t.message ?: "unknown error")
                    }

                    override fun onResponse(
                        call: Call<GitUserResponse>?,
                        response: Response<GitUserResponse>
                    ) {
                        Log.d(TAG, "got a response $response")
                        if (response.isSuccessful) {
                            val users = response.body()?.items ?: emptyList()
                            onSuccess(users)
                        } else {
                            onError(response.errorBody()?.string() ?: "Unknown error")
                        }
                    }
                }
            )
        }
    }
}