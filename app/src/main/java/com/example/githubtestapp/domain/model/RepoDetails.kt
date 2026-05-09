package com.example.githubtestapp.domain.model

data class RepoDetails(
    val id: Long,
    val name: String,
    val forksCount: Int,
    val watchersCount: Int,
    val ownerName: String,
    val ownerAvatarUrl: String
)