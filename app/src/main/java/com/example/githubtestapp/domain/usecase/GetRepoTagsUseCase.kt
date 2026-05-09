package com.example.githubtestapp.domain.usecase

import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.domain.repository.GithubRepository
import javax.inject.Inject

class GetRepoTagsUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(
        owner: String,
        repo: String
    ): Result<List<RepoTag>> {
        return repository.getRepoTags(owner, repo)
    }
}