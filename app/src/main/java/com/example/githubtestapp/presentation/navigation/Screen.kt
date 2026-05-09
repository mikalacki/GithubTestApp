package com.example.githubtestapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object UserRepos : Screen("user_repos")

    data object RepoDetailsRoute : Screen("repo_details/{repoName}") {
        fun createRoute(repoName: String): String {
            return "repo_details/$repoName"
        }
    }
}