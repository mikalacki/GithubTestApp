package com.example.githubtestapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtestapp.domain.usecase.GetRepoDetailsUseCase
import com.example.githubtestapp.domain.usecase.GetRepoTagsUseCase
import com.example.githubtestapp.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase,
    private val getRepoTagsUseCase: GetRepoTagsUseCase
) : ViewModel() {

    private val _detailsState =
        MutableStateFlow<UiState<RepoDetailsUiModel>>(UiState.Idle)

    val detailsState: StateFlow<UiState<RepoDetailsUiModel>> =
        _detailsState.asStateFlow()

    fun getRepoDetails(
        owner: String = "octocat",
        repo: String
    ) {
        viewModelScope.launch {
            _detailsState.value = UiState.Loading

            val detailsResult = getRepoDetailsUseCase(owner, repo)
            val tagsResult = getRepoTagsUseCase(owner, repo)

            _detailsState.value = if (
                detailsResult.isSuccess && tagsResult.isSuccess
            ) {
                UiState.Success(
                    RepoDetailsUiModel(
                        repoDetails = detailsResult.getOrThrow(),
                        tags = tagsResult.getOrThrow()
                    )
                )
            } else {
                val message =
                    detailsResult.exceptionOrNull()?.message
                        ?: tagsResult.exceptionOrNull()?.message
                        ?: "Unknown error"

                UiState.Error(message)
            }
        }
    }
}