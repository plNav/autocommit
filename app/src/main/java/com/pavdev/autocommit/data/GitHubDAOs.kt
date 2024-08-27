package com.pavdev.autocommit.data

import kotlinx.serialization.Serializable

@Serializable
data class GitHubContentResponse(
    val name: String,
    val path: String,
    val sha: String,
    val content: String?,
    val encoding: String?
)

@Serializable
data class UpdateContentRequest(
    val message : String,
    val content: String,
    val sha: String
)

