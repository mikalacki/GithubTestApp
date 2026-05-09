package com.example.githubtestapp.presentation.details

import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag

data class RepoDetailsUiModel(
    val repoDetails: RepoDetails,
    val tags: List<RepoTag>
)