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

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("repos/{username}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("username") username: String,
        @Path("repo") repo: String,
        @Path("path") path: String
    ): Response<GitHubContentResponse>

    @PUT("repos/{username}/{repo}/contents/{path}")
    suspend fun updateFileContents(
        @Path("username") username: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Body requestBody: GitHubUpdateRequest
    ): Response<JsonObject>
}