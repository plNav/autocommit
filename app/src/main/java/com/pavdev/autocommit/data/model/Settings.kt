package com.pavdev.autocommit.data.model

class Settings(
    val username: String,
    val repository: String,
    val defaultCommit: String,
    val defaultRepoFile: String,
    val defaultAddedLine: String,
) {

    fun validate(): String? {
        if (username.isBlank() || username == DEFAULT_SETTINGS.username) {
            return "Invalid Username"
        }
        if (repository.isBlank() || repository == DEFAULT_SETTINGS.repository) {
            return "Invalid Repository"
        }
        if (defaultCommit.isBlank()) {
            return "Default commit cannot be empty"
        }
        if (defaultRepoFile.isBlank()) {
            return "Default repo file cannot be empty"
        }
        if (defaultAddedLine.isBlank()) {
            return "Default Added Line cannot be empty"
        }
        return null
    }

    companion object {
        val DEFAULT_SETTINGS = Settings(
            username = "your-github-username",
            repository = "your-github-repository",
            defaultCommit = "Updated README.md by AutoCommit",
            defaultRepoFile = "README.md",
            defaultAddedLine = " \n > Line from AutoCommit"
        )
    }
}

