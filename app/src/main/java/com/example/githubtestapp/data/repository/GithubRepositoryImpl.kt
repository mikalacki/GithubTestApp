package com.example.githubtestapp.data.repository

import com.example.githubtestapp.data.mapper.toDomain
import com.example.githubtestapp.data.remote.api.GithubApi
import com.example.githubtestapp.data.remote.util.ApiCallHandler
import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.domain.model.User
import com.example.githubtestapp.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val apiCallHandler: ApiCallHandler
) : GithubRepository {

    override suspend fun getUserDetails(username: String): Result<User> {
        return apiCallHandler.safeApiCall {
            githubApi.getUserDetails(username).toDomain()
        }
    }

    override suspend fun getUserRepos(username: String): Result<List<Repo>> {
        return apiCallHandler.safeApiCall {
            githubApi.getUserRepos(username).map { it.toDomain() }
        }
    }

    override suspend fun getRepoDetails(
        owner: String,
        repo: String
    ): Result<RepoDetails> {
        return apiCallHandler.safeApiCall {
            githubApi.getRepoDetails(owner, repo).toDomain()
        }
    }

    override suspend fun getRepoTags(
        owner: String,
        repo: String
    ): Result<List<RepoTag>> {
        return apiCallHandler.safeApiCall {
            githubApi.getRepoTags(owner, repo).map { it.toDomain() }
        }
    }
}