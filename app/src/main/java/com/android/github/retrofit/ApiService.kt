package com.android.github.retrofit

import com.android.github.models.ProfileDetail
import com.android.github.models.RepositoryList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{userId}")
    suspend fun getProfile(@Path("userId")userId:String): Response<ProfileDetail>?

    @GET("{userId}/repos")
    suspend fun getRepositories(@Path("userId")userId:String): Response<RepositoryList>
}