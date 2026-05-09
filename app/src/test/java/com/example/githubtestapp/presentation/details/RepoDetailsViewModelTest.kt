package com.example.githubtestapp.presentation.details

import app.cash.turbine.test
import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.domain.usecase.GetRepoDetailsUseCase
import com.example.githubtestapp.domain.usecase.GetRepoTagsUseCase
import com.example.githubtestapp.presentation.common.UiState
import com.example.githubtestapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRepoDetailsUseCase: GetRepoDetailsUseCase = mockk()
    private val getRepoTagsUseCase: GetRepoTagsUseCase = mockk()

    private lateinit var viewModel: RepoDetailsViewModel

    @Test
    fun `getRepoDetails emits Success when details and tags succeed`() = runTest {
        val repoDetails = RepoDetails(
            id = 1,
            name = "Hello-World",
            forksCount = 10,
            watchersCount = 20,
            ownerName = "octocat",
            ownerAvatarUrl = "avatar"
        )

        val tags = listOf(
            RepoTag(
                name = "v1.0.0",
                commitSha = "abc123"
            )
        )

        coEvery {
            getRepoDetailsUseCase("octocat", "Hello-World")
        } returns Result.success(repoDetails)

        coEvery {
            getRepoTagsUseCase("octocat", "Hello-World")
        } returns Result.success(tags)

        viewModel = RepoDetailsViewModel(
            getRepoDetailsUseCase = getRepoDetailsUseCase,
            getRepoTagsUseCase = getRepoTagsUseCase
        )

        viewModel.detailsState.test {
            assert(awaitItem() is UiState.Idle)

            viewModel.getRepoDetails(
                owner = "octocat",
                repo = "Hello-World"
            )

            val successState = awaitItem()

            assert(successState is UiState.Success)

            val data = (successState as UiState.Success).data

            assert(data.repoDetails == repoDetails)
            assert(data.tags == tags)
        }
    }

    @Test
    fun `getRepoDetails emits Error when details call fails`() = runTest {

        coEvery {
            getRepoDetailsUseCase("octocat", "Hello-World")
        } returns Result.failure(Throwable("Repo details error"))

        coEvery {
            getRepoTagsUseCase("octocat", "Hello-World")
        } returns Result.success(emptyList())

        viewModel = RepoDetailsViewModel(
            getRepoDetailsUseCase = getRepoDetailsUseCase,
            getRepoTagsUseCase = getRepoTagsUseCase
        )

        viewModel.detailsState.test {
            assert(awaitItem() is UiState.Idle)

            viewModel.getRepoDetails(
                owner = "octocat",
                repo = "Hello-World"
            )

            val errorState = awaitItem()

            assert(errorState is UiState.Error)

            val message = (errorState as UiState.Error).message

            assert(message == "Repo details error")
        }
    }

    @Test
    fun `getRepoDetails emits Error when tags call fails`() = runTest {

        val repoDetails = RepoDetails(
            id = 1,
            name = "Hello-World",
            forksCount = 10,
            watchersCount = 20,
            ownerName = "octocat",
            ownerAvatarUrl = "avatar"
        )

        coEvery {
            getRepoDetailsUseCase("octocat", "Hello-World")
        } returns Result.success(repoDetails)

        coEvery {
            getRepoTagsUseCase("octocat", "Hello-World")
        } returns Result.failure(Throwable("Tags error"))

        viewModel = RepoDetailsViewModel(
            getRepoDetailsUseCase = getRepoDetailsUseCase,
            getRepoTagsUseCase = getRepoTagsUseCase
        )

        viewModel.detailsState.test {
            assert(awaitItem() is UiState.Idle)

            viewModel.getRepoDetails(
                owner = "octocat",
                repo = "Hello-World"
            )

            val errorState = awaitItem()

            assert(errorState is UiState.Error)

            val message = (errorState as UiState.Error).message

            assert(message == "Tags error")
        }
    }
}