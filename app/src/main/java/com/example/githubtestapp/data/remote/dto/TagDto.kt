package com.example.githubtestapp.data.remote.dto

data class TagDto(
    val name: String,
    val commit: CommitDto
)

data class CommitDto(
    val sha: String
)