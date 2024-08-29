package com.pavdev.autocommit.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileManager {

    companion object {

        private const val TARGET_FILE = "autocommit-secret.txt"

        fun getFileOutputStream(filesDir: File): FileOutputStream {
            val file = File(filesDir, TARGET_FILE)
            checkExists(file)
            return FileOutputStream(file)
        }

        fun getFileInputStream(filesDir: File): FileInputStream {
            val file = File(filesDir, TARGET_FILE)
            checkExists(file)
            return FileInputStream(file)
        }

        private fun checkExists(file: File) {
            if (!file.exists()) file.createNewFile()
        }
    }
}