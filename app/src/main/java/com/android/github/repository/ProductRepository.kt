package com.android.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.github.models.ProfileDetail
import com.android.github.models.RepositoryList
import com.android.github.retrofit.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    private val _profile = MutableLiveData<ProfileDetail>()
    val profile: LiveData<ProfileDetail>
        get() = _profile

    private val _repoList = MutableLiveData<RepositoryList>()
    val repoList: LiveData<RepositoryList>
        get() = _repoList

    suspend fun getProfile(userId: String) {
        val result = apiService.getProfile(userId)
        if (result != null) {
            if (result.isSuccessful && result?.body() != null) {
                _profile.postValue(result.body())
            }
        }
    }

    suspend fun getRepoList(userId: String) {
        val result = apiService.getRepositories(userId)
        if (result.isSuccessful && result.body() != null) {
            _repoList.postValue(result.body())
        }
    }

}