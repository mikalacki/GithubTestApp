package com.example.githubtestapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.presentation.common.ErrorContent
import com.example.githubtestapp.presentation.common.LoadingContent
import com.example.githubtestapp.presentation.common.UiState
import com.example.githubtestapp.ui.GithubTestAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    repoName: String,
    onBackClick: () -> Unit,
    viewModel: RepoDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.detailsState.collectAsStateWithLifecycle()

    LaunchedEffect(repoName) {
        viewModel.getRepoDetails(repo = repoName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repo Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                UiState.Idle -> {
                }

                UiState.Loading -> {
                    LoadingContent()

                }

                is UiState.Error -> {
                    ErrorContent(
                        message = currentState.message,
                        onRetryClick = {
                            viewModel.getRepoDetails(repo = repoName)
                        }
                    )
                }

                is UiState.Success -> {
                    val data = currentState.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            RepoHeader(data.repoDetails)
                        }

                        if (data.tags.isEmpty()) {
                            item {
                                Text(
                                    text = "No tags found.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else {
                            items(data.tags) { tag ->
                                TagItem(tag)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoHeaderPreview() {
    GithubTestAppTheme {
        RepoHeader(
            repoDetails = RepoDetails(
                id = 1,
                name = "Hello-World",
                forksCount = 10,
                watchersCount = 25,
                ownerName = "octocat",
                ownerAvatarUrl = ""
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TagItemPreview() {
    GithubTestAppTheme {
        TagItem(
            tag = RepoTag(
                name = "v1.0.0",
                commitSha = "abc123def456"
            )
        )
    }
}