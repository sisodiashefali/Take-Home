package com.android.github.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.github.models.ProfileDetail
import com.android.github.models.RepositoryList
import com.android.github.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val productsLiveData: LiveData<ProfileDetail>
        get() = repository.profile

    val repoListLiveData: LiveData<RepositoryList>
        get() = repository.repoList

    fun loadGitProfile(userId: String) {
        val user=userId
        viewModelScope.launch {
            repository.getProfile(userId)
            repository.getRepoList(userId)
        }
    }

}

