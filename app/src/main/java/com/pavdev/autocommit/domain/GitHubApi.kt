package com.pavdev.autocommit.domain

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pavdev.autocommit.domain.GitHubService.Companion.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

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

// Create Service from Interface
object GitHubApi {
    val retrofitService: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }
}