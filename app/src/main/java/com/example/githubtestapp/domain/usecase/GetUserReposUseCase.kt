package com.example.githubtestapp.domain.usecase

import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): Result<List<Repo>> {
        return repository.getUserRepos(username)
    }
}