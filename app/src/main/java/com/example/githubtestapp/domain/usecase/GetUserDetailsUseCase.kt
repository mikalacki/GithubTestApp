package com.example.githubtestapp.domain.usecase

import com.example.githubtestapp.domain.model.User
import com.example.githubtestapp.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(username: String): Result<User> {
        return repository.getUserDetails(username)
    }
}