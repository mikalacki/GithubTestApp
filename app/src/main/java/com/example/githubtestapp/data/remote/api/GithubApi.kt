package com.example.githubtestapp.data.remote.api

import com.example.githubtestapp.data.remote.dto.RepoDetailsDto
import com.example.githubtestapp.data.remote.dto.RepoDto
import com.example.githubtestapp.data.remote.dto.TagDto
import com.example.githubtestapp.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): UserDto

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<RepoDto>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepoDetailsDto

    @GET("repos/{owner}/{repo}/tags")
    suspend fun getRepoTags(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<TagDto>
}