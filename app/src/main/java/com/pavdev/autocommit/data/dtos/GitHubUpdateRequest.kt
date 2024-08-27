package com.pavdev.autocommit.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class GitHubUpdateRequest(
    val message: String,
    val content: String,
    val sha: String
)

