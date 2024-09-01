package com.pavdev.autocommit

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pavdev.autocommit.util.CryptoManager
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@RunWith(AndroidJUnit4::class)
class CryptoManagerTest {

    private lateinit var cryptoManager: CryptoManager

    @Before
    fun setUp() {
        cryptoManager = CryptoManager()
    }

    @Test
    fun testEncryptAndDecrypt() {

        val plainText = "Hello, World!".toByteArray()
        val outputStream = ByteArrayOutputStream()

        cryptoManager.encrypt(plainText, outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val decryptedBytes = cryptoManager.decrypt(inputStream)

        assertArrayEquals(plainText, decryptedBytes)
    }

    @Test
    fun testDecryptWhenNull() {
        val inputStream = ByteArrayInputStream(ByteArrayOutputStream().toByteArray())

       assertThrows(NegativeArraySizeException::class.java) {
            cryptoManager.decrypt(inputStream)
        }

    }
}