package com.example.githubtestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)