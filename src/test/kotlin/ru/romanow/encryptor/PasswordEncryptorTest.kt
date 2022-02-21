package ru.romanow.encryptor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class PasswordEncryptorTest {

    @ParameterizedTest
    @MethodSource("factory")
    fun testDecrypt(encrypted: String, expected: String) {
        val password = "test"
        assertThat(decrypt(password, encrypted)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun factory() = arrayOf(
            arrayOf("U2FsdGVkX182or9FpXaH+oKO2qY0DsLR0bCgZfO5vbo=", "Hello, World"),
            arrayOf("U2FsdGVkX182i+6dxkkKNitDhiQ1KAbax2Tj5UZkRiWFpKa/h1yan50iPniX0LdZ83fh8jeS8qYoI2MuyLYRSA==", "That which does not kill us makes us stronger"),
            arrayOf("U2FsdGVkX18bdt9q1ob6vUaw3VpNkMZOPUFuWRdQ2o6K6LIQ2TeypReeNe32Jj1aDJPnct8RoEyhuMFUBrmDQvnTxeICFjGWtWkqUoqKWHL+72ZSQYs8EO8kBzbsok1l", "Live as if you were to die tomorrow. Learn as if you were to live forever"),
            arrayOf("U2FsdGVkX1+F7EAzFFxtebr+kDlqto+v7QDE/mSmKifx65zmhX34dEi+/sKGj2+Jt2u9sXr+FX2tliLHy8SCy8YCqMXGvvGdND0mUqNZdZI=", "Do what you can, with what you have, where you are"),
        )
    }
}