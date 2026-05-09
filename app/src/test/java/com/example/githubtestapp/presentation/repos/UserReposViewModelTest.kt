package com.example.githubtestapp.presentation.repos

import app.cash.turbine.test
import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.usecase.GetUserReposUseCase
import com.example.githubtestapp.presentation.common.UiState
import com.example.githubtestapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserReposViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUserReposUseCase: GetUserReposUseCase = mockk()

    private lateinit var viewModel: UserReposViewModel

    @Test
    fun `getUserRepos emits Success when API call succeeds`() = runTest {

        val repos = listOf(
            Repo(
                id = 1,
                name = "Test Repo",
                openIssuesCount = 5
            )
        )

        coEvery {
            getUserReposUseCase("octocat")
        } returns Result.success(repos)

        viewModel = UserReposViewModel(getUserReposUseCase)

        viewModel.reposState.test {

            assert(awaitItem() is UiState.Idle)

            viewModel.getUserRepos()

            val successState = awaitItem()

            assert(successState is UiState.Success)

            val data = (successState as UiState.Success).data

            assert(data == repos)
        }
    }

    @Test
    fun `getUserRepos emits Error when API call fails`() = runTest {

        coEvery {
            getUserReposUseCase("octocat")
        } returns Result.failure(Throwable("Network error"))

        viewModel = UserReposViewModel(getUserReposUseCase)

        viewModel.reposState.test {

            assert(awaitItem() is UiState.Idle)

            viewModel.getUserRepos()

            val errorState = awaitItem()

            assert(errorState is UiState.Error)

            val message = (errorState as UiState.Error).message

            assert(message == "Network error")
        }
    }
}