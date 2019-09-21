package com.surya.githubusersearchapp.data.remote

import com.surya.githubusersearchapp.BuildConfig
import com.surya.githubusersearchapp.data.model.GitUserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by suryamudti on 21/09/2019.
 */
interface GithubService {

    /** retrofit
     * Get github users
     */
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<GitUserResponse> // returns a UserSearchResponse class

    /**
     * create base url and build retrofit client call
     */
    companion object {
        fun create(): GithubService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java) // Create an implementation of the API endpoints defined by the service interface (GithubService).
        }
    }
}