package com.example.githubtestapp.presentation.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.usecase.GetUserReposUseCase
import com.example.githubtestapp.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReposViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _reposState = MutableStateFlow<UiState<List<Repo>>>(UiState.Idle)
    val reposState: StateFlow<UiState<List<Repo>>> = _reposState.asStateFlow()

    init {
        getUserRepos()
    }

    fun getUserRepos(username: String = "octocat") {
        viewModelScope.launch {
            _reposState.value = UiState.Loading

            val result = getUserReposUseCase(username)

            _reposState.value = result.fold(
                onSuccess = { repos ->
                    UiState.Success(repos)
                },
                onFailure = { error ->
                    UiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}