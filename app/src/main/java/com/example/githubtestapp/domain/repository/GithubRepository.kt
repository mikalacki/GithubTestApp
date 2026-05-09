package com.example.githubtestapp.domain.repository

import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.domain.model.User

interface GithubRepository {

    suspend fun getUserDetails(username: String): Result<User>

    suspend fun getUserRepos(username: String): Result<List<Repo>>

    suspend fun getRepoDetails(
        owner: String,
        repo: String
    ): Result<RepoDetails>

    suspend fun getRepoTags(
        owner: String,
        repo: String
    ): Result<List<RepoTag>>
}