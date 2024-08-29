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

    private val retrofitService: GitHubService by lazy {
        createRetrofit().create(GitHubService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
            .client(createOkHttpClient())
            .baseUrl(GitHubService.BASE_URL)
            .build()
    }

    fun configure(token: String? = null, username: String? = null, repo: String? = null) {
        if (token != null) this.token = token
        if (username != null) this.username = username
        if (repo != null) this.repo = repo
        _retrofitService = createRetrofit().create(GitHubService::class.java)
    }

    suspend fun getRepoContents(path: String): Response<GitHubContentResponse> {
        val user = username ?: throw IllegalStateException("Username not set")
        val repository = repo ?: throw IllegalStateException("Repository not set")
        return service.getRepoContents(user, repository, path)
    }

    suspend fun updateFileContents(
        path: String,
        requestBody: GitHubUpdateRequest
    ): Response<JsonObject> {
        val user = username ?: throw IllegalStateException("Username not set")
        val repository = repo ?: throw IllegalStateException("Repository not set")
        return service.updateFileContents(user, repository, path, requestBody)
    }
}