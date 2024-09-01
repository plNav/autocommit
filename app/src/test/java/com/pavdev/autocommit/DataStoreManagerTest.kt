package com.pavdev.autocommit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pavdev.autocommit.data.models.Settings
import com.pavdev.autocommit.util.DataStoreManager
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
@ExperimentalUnsignedTypes
class DataStoreManagerTest {
    private lateinit var dataStoreManager: DataStoreManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStoreManager = DataStoreManager(context)
    }

    @Test
    fun testWriteAndRead() = runTest {
        val settings = Settings(
            username = "testUser",
            repository = "testRepo",
            defaultCommit = "Initial commit",
            defaultRepoFile = "README.md",
            defaultAddedLine = "Added README"
        )

        dataStoreManager.saveSettings(settings)
        val savedSettings = dataStoreManager.getSettings()

        assertEquals(settings.username, savedSettings.username)
        assertEquals(settings.repository, savedSettings.repository)
        assertEquals(settings.defaultCommit, savedSettings.defaultCommit)
        assertEquals(settings.defaultRepoFile, savedSettings.defaultRepoFile)
        assertEquals(settings.defaultAddedLine, savedSettings.defaultAddedLine)
    }


    @Test
    fun testEmptyValues() = runTest {
        val settings = Settings(
            username = "",
            repository = "",
            defaultCommit = "",
            defaultRepoFile = "",
            defaultAddedLine = ""
        )

        dataStoreManager.saveSettings(settings)
        val savedSettings = dataStoreManager.getSettings()

        assertEquals("", savedSettings.username)
        assertEquals("", savedSettings.repository)
        assertEquals("", savedSettings.defaultCommit)
        assertEquals("", savedSettings.defaultRepoFile)
        assertEquals("", savedSettings.defaultAddedLine)
    }
    @Test
    fun testDefaultValuesIfNull() = runTest {
        dataStoreManager.clearSettings()

        val retrievedSettings = dataStoreManager.getSettings()

        assertEquals(Settings.DEFAULT_SETTINGS.username, retrievedSettings.username)
        assertEquals(Settings.DEFAULT_SETTINGS.repository, retrievedSettings.repository)
        assertEquals(Settings.DEFAULT_SETTINGS.defaultCommit, retrievedSettings.defaultCommit)
        assertEquals(Settings.DEFAULT_SETTINGS.defaultRepoFile, retrievedSettings.defaultRepoFile)
        assertEquals(Settings.DEFAULT_SETTINGS.defaultAddedLine, retrievedSettings.defaultAddedLine)

    }
}