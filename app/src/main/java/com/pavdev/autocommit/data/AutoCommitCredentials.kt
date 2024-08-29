package com.pavdev.autocommit.data

data class AutoCommitCredentials(
    val username: String,
    val repository: String,
    val defaultCommit: String,
    val defaultRepoFile: String,
    val defaultAddedLine: String
)

val DEFAULT_CREDENTIALS = AutoCommitCredentials(
    username = "your-github-username",
    repository = "your-github-repository",
    defaultCommit = "Update README.md with AutoCommit",
    defaultRepoFile = "README.md",
    defaultAddedLine = " \n > Line from AutoCommit"
)