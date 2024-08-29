package com.pavdev.autocommit.domain

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pavdev.autocommit.data.dtos.GitHubContentResponse
import com.pavdev.autocommit.data.dtos.GitHubUpdateRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit

class GitHubApi {

    private var token: String? = null
    private var username: String? = null
    private var repo: String? = null

    private var _retrofitService: GitHubService? = null

    private val jsonConverter = Json { ignoreUnknownKeys = true }

    private val service: GitHubService
        get() = _retrofitService ?: retrofitService

    // Interceptor to add the authorization token to every request
    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .apply {
                if (token != null) {
                    addHeader("Authorization", "token $token")
                }
            }
            .build()
        chain.proceed(newRequest)
    }

    // Lazy initialization of the Retrofit service
    private val retrofitService: GitHubService by lazy {
        createRetrofit().create(GitHubService::class.java)
    }

    // Method to create OkHttpClient with the current token
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    // Method to create Retrofit instance
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
            .client(createOkHttpClient())
            .baseUrl(GitHubService.BASE_URL)
            .build()
    }

    // Method to set the token, username, and repository
    fun configure(token: String, username: String, repo: String) {
        this.token = token
        this.username = username
        this.repo = repo
        // Re-initialize the service with the new token
        _retrofitService = createRetrofit().create(GitHubService::class.java)
    }

    // Function to call getRepoContents with the configured username and repo
    suspend fun getRepoContents(path: String): Response<GitHubContentResponse> {
        val user = username ?: throw IllegalStateException("Username not set")
        val repository = repo ?: throw IllegalStateException("Repository not set")
        return service.getRepoContents(user, repository, path)
    }

    // Function to call updateFileContents with the configured username and repo
    suspend fun updateFileContents(path: String, requestBody: GitHubUpdateRequest): Response<JsonObject> {
        val user = username ?: throw IllegalStateException("Username not set")
        val repository = repo ?: throw IllegalStateException("Repository not set")
        return service.updateFileContents(user, repository, path, requestBody)
    }
}