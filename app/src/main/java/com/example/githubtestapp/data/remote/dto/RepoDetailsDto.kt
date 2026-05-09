package com.example.githubtestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepoDetailsDto(
    val id: Long,
    val name: String,

    @SerializedName("forks_count")
    val forksCount: Int,

    @SerializedName("watchers_count")
    val watchersCount: Int,

    val owner: OwnerDto
)

data class OwnerDto(
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)