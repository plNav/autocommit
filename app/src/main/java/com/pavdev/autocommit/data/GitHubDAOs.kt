package com.pavdev.autocommit.data

import kotlinx.serialization.Serializable

@Serializable
data class GitHubContent(
    val name: String,
    val path: String,
    val sha: String,
    val content: String?,
    val encoding: String?
)

@Serializable
data class UpdateContentResponse(
    val content: GitHubContent?,
    val commit: GitHubCommit
)

@Serializable
data class UpdateContentRequest(
    val message : String,
    val content: String,
    val sha: String
)

@Serializable
data class GitHubCommit(
    val sha: String,
    val url: String
)