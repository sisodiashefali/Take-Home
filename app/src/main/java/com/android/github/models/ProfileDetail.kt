package com.android.github.models

data class ProfileDetail(
    val name: String? = "",
    val avatar_url: String? = "",
    val error: String? = "",
    val isLoading: Boolean = false
)
