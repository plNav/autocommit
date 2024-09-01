package com.pavdev.autocommit

import com.pavdev.autocommit.util.FileManager
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class FileManagerTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun testWriteAndReadText() {
        val filesDir = tempFolder.root
        val targetFile = File(filesDir, FileManager.TARGET_FILE)
        val data = "Some data"

        val outputStream = FileManager.getFileOutputStream(filesDir)
        outputStream.write(data.toByteArray())
        outputStream.close()

        val content = targetFile.readText()
        assertEquals(data, content)
    }

    @Test
    fun testCheckExistsInput() {
        val filesDir = tempFolder.root
        val targetFile = File(filesDir, FileManager.TARGET_FILE)

        FileManager.getFileInputStream(filesDir)

        assertTrue(targetFile.exists())
    }

    @Test
    fun testCheckExistsOutput() {
        val filesDir = tempFolder.root
        val targetFile = File(filesDir, FileManager.TARGET_FILE)

        FileManager.getFileOutputStream(filesDir)

        assertTrue(targetFile.exists())
    }
}