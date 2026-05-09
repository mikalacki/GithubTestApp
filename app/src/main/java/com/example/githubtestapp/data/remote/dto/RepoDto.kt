package com.example.githubtestapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepoDto(
    val id: Long,
    val name: String,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int
)