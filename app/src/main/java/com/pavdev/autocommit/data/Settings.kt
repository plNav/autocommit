package com.pavdev.autocommit.data

class Settings(
    val username: String,
    val repository: String,
    val defaultCommit: String,
    val defaultRepoFile: String,
    val defaultAddedLine: String,
){

    companion object {
        val DEFAULT_SETTINGS = Settings(
            username = "your-github-username",
            repository = "your-github-repository",
            defaultCommit = "Update README.md with AutoCommit",
            defaultRepoFile = "README.md",
            defaultAddedLine = " \n > Line from AutoCommit"
        )
    }
}

