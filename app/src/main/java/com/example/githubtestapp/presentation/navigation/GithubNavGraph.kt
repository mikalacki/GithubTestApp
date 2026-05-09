package com.example.githubtestapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubtestapp.presentation.details.RepoDetailsScreen
import com.example.githubtestapp.presentation.repos.UserReposScreen

@Composable
fun GithubNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.UserRepos.route
    ) {
        composable(Screen.UserRepos.route) {
            UserReposScreen(
                onRepoClick = { repoName ->
                    navController.navigate(
                        Screen.RepoDetailsRoute.createRoute(repoName)
                    )
                }
            )
        }

        composable(
            route = Screen.RepoDetailsRoute.route,
            arguments = listOf(
                navArgument("repoName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val repoName = backStackEntry.arguments?.getString("repoName").orEmpty()

            RepoDetailsScreen(
                repoName = repoName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}