package com.example.githubtestapp.presentation.repos


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.presentation.common.EmptyContent
import com.example.githubtestapp.presentation.common.ErrorContent
import com.example.githubtestapp.presentation.common.LoadingContent
import com.example.githubtestapp.presentation.common.UiState
import com.example.githubtestapp.ui.GithubTestAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReposScreen(
    onRepoClick: (String) -> Unit,
    viewModel: UserReposViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getUserRepos()
    }
    val state by viewModel.reposState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Github Repositories")
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

                UiState.Idle -> Unit

                UiState.Loading -> {
                    LoadingContent()
                }

                is UiState.Error -> {
                    ErrorContent(
                        message = currentState.message,
                        onRetryClick = {
                            viewModel.getUserRepos()
                        }
                    )
                }

                is UiState.Success -> {
                    val repos = currentState.data

                    if (repos.isEmpty()) {
                        EmptyContent(message = "No repositories found.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(repos) { repo ->
                                RepoItem(
                                    repo = repo,
                                    onClick = {
                                        onRepoClick(repo.name)
                                    }
                                )
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
private fun RepoItemPreview() {
    GithubTestAppTheme {
        RepoItem(
            repo = Repo(
                id = 1,
                name = "Hello-World",
                openIssuesCount = 3
            ),
            onClick = {}
        )
    }
}