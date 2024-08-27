package com.pavdev.autocommit.domain.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pavdev.autocommit.data.GitHubContent
import com.pavdev.autocommit.data.UpdateContentRequest
import com.pavdev.autocommit.data.UpdateContentResponse
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/** Create a file 'credentials.kt' in network package
private const val BASE_URL = "https://api.github.com/"
private const val TOKEN = "your_github_personal_access_token"
private const val USERNAME = "your-github-username"
private const val REPO = "your-repo-target"
*/

// Setup JSON converter with kotlinx.serialization
private val jsonConverter = Json { ignoreUnknownKeys = true }

// Interceptor to add the authorization token to every request
private val authInterceptor = Interceptor { chain ->
    val newRequest = chain.request().newBuilder()
        .addHeader("Authorization", "token $TOKEN")
        .build()
    chain.proceed(newRequest)
}

// Configure OkHttpClient with the authInterceptor
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .build()

// Configure Retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(jsonConverter.asConverterFactory("application/json".toMediaType()))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

// GitHubService interface defining API endpoints
interface GitHubService {
    @GET("repos/$USERNAME/$REPO/contents/{path}")
    suspend fun getRepoContents(
        @Path("path") path: String
    ): Response<GitHubContent>

    @PUT("repos/$USERNAME/$REPO/contents/{path}")
    suspend fun updateFileContents(
        @Path("path") path: String,
        @Body requestBody: UpdateContentRequest
    ): Response<UpdateContentResponse>
}

object GitHubApi {
    val retrofitService: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }
}