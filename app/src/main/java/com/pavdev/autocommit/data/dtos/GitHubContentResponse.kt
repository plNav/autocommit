package com.pavdev.autocommit.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class GitHubContentResponse(
    val name: String,
    val path: String,
    val sha: String,
    val content: String?,
    val encoding: String?
)