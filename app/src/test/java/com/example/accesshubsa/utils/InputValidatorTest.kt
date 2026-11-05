package com.example.accesshubsa.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidatorTest {

    @Test
    fun validEmail_isAccepted() {
        assertTrue(InputValidator.isValidEmail("thabo@example.co.za"))
    }

    @Test
    fun emailWithSurroundingSpaces_isAccepted() {
        assertTrue(InputValidator.isValidEmail("  thabo@example.com  "))
    }

    @Test
    fun emailWithoutAtSymbol_isRejected() {
        assertFalse(InputValidator.isValidEmail("thabo.example.com"))
    }

    @Test
    fun emailWithoutDomainDot_isRejected() {
        assertFalse(InputValidator.isValidEmail("thabo@example"))
    }

    @Test
    fun emptyEmail_isRejected() {
        assertFalse(InputValidator.isValidEmail(""))
    }

    @Test
    fun passwordWithEightCharacters_isAccepted() {
        assertTrue(InputValidator.isValidPassword("abcd1234"))
    }

    @Test
    fun passwordWithSevenCharacters_isRejected() {
        assertFalse(InputValidator.isValidPassword("abcd123"))
    }

    @Test
    fun emptyPassword_isRejected() {
        assertFalse(InputValidator.isValidPassword(""))
    }

    @Test
    fun matchingPasswords_areAccepted() {
        assertTrue(InputValidator.passwordsMatch("abcd1234", "abcd1234"))
    }

    @Test
    fun differentPasswords_areRejected() {
        assertFalse(InputValidator.passwordsMatch("abcd1234", "abcd12345"))
    }
}
