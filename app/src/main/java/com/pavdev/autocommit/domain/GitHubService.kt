package com.pavdev.autocommit.domain

import com.pavdev.autocommit.data.dtos.GitHubContentResponse
import com.pavdev.autocommit.data.dtos.GitHubUpdateRequest
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GitHubService {
    @GET("repos/$USERNAME/$REPO/contents/{path}")
    suspend fun getRepoContents(
        @Path("path") path: String
    ): Response<GitHubContentResponse>

    @PUT("repos/$USERNAME/$REPO/contents/{path}")
    suspend fun updateFileContents(
        @Path("path") path: String,
        @Body requestBody: GitHubUpdateRequest
    ): Response<JsonObject>
}