package com.example.githubtestapp.domain.usecase

import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.repository.GithubRepository
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(
        owner: String,
        repo: String
    ): Result<RepoDetails> {
        return repository.getRepoDetails(owner, repo)
    }
}