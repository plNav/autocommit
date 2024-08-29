package com.pavdev.autocommit.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pavdev.autocommit.data.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStoreManager(context: Context) {

    private val dataStore = context.autoCommitCredentialsDataStore

    companion object {

        private val Context.autoCommitCredentialsDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "auto_commit_credentials"
        )

        private object PreferenceKeys {
            val USERNAME = stringPreferencesKey("username")
            val REPOSITORY = stringPreferencesKey("repository")
            val DEFAULT_COMMIT = stringPreferencesKey("default_commit")
            val DEFAULT_REPO_FILE = stringPreferencesKey("default_repo_file")
            val DEFAULT_ADDED_LINE = stringPreferencesKey("default_added_line")
        }
    }


   private val autoCommitCredentialsFlow: Flow<Settings> = dataStore.data
        .map { preferences ->
            Settings(
                username = preferences[PreferenceKeys.USERNAME]
                    ?: Settings.DEFAULT_SETTINGS.username,
                repository = preferences[PreferenceKeys.REPOSITORY]
                    ?: Settings.DEFAULT_SETTINGS.repository,
                defaultCommit = preferences[PreferenceKeys.DEFAULT_COMMIT]
                    ?: Settings.DEFAULT_SETTINGS.defaultCommit,
                defaultRepoFile = preferences[PreferenceKeys.DEFAULT_REPO_FILE]
                    ?: Settings.DEFAULT_SETTINGS.defaultRepoFile,
                defaultAddedLine = preferences[PreferenceKeys.DEFAULT_ADDED_LINE]
                    ?: Settings.DEFAULT_SETTINGS.defaultAddedLine
            )
        }

    suspend fun saveSettings(credentials: Settings) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USERNAME] = credentials.username
            preferences[PreferenceKeys.REPOSITORY] = credentials.repository
            preferences[PreferenceKeys.DEFAULT_COMMIT] = credentials.defaultCommit
            preferences[PreferenceKeys.DEFAULT_REPO_FILE] = credentials.defaultRepoFile
            preferences[PreferenceKeys.DEFAULT_ADDED_LINE] = credentials.defaultAddedLine
        }
    }

    suspend fun getSettings(): Settings {
        val preferences = dataStore.data.first()
        return Settings(
            username = preferences[PreferenceKeys.USERNAME]
                ?: Settings.DEFAULT_SETTINGS.username,
            repository = preferences[PreferenceKeys.REPOSITORY]
                ?: Settings.DEFAULT_SETTINGS.repository,
            defaultCommit = preferences[PreferenceKeys.DEFAULT_COMMIT]
                ?: Settings.DEFAULT_SETTINGS.defaultCommit,
            defaultRepoFile = preferences[PreferenceKeys.DEFAULT_REPO_FILE]
                ?: Settings.DEFAULT_SETTINGS.defaultRepoFile,
            defaultAddedLine = preferences[PreferenceKeys.DEFAULT_ADDED_LINE]
                ?: Settings.DEFAULT_SETTINGS.defaultAddedLine
        )
    }
}