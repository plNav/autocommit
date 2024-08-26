package com.pavdev.autocommit.domain.services

import retrofit2.http.GET

interface GitHubService {
    @GET("photos")
    fun getPhotos(): String
}