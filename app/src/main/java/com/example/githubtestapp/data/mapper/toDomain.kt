package com.example.githubtestapp.data.mapper

import com.example.githubtestapp.data.remote.dto.RepoDetailsDto
import com.example.githubtestapp.data.remote.dto.RepoDto
import com.example.githubtestapp.data.remote.dto.TagDto
import com.example.githubtestapp.data.remote.dto.UserDto
import com.example.githubtestapp.domain.model.Repo
import com.example.githubtestapp.domain.model.RepoDetails
import com.example.githubtestapp.domain.model.RepoTag
import com.example.githubtestapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        username = login,
        avatarUrl = avatarUrl
    )
}

fun RepoDto.toDomain(): Repo {
    return Repo(
        id = id,
        name = name,
        openIssuesCount = openIssuesCount
    )
}

fun RepoDetailsDto.toDomain(): RepoDetails {
    return RepoDetails(
        id = id,
        name = name,
        forksCount = forksCount,
        watchersCount = watchersCount,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl
    )
}

fun TagDto.toDomain(): RepoTag {
    return RepoTag(
        name = name,
        commitSha = commit.sha
    )
}