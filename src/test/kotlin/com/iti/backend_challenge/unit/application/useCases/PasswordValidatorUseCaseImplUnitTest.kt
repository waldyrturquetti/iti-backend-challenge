package com.iti.backend_challenge.unit.application.useCases

import com.iti.backend_challenge.application.exceptions.ParameterizationNotFoundException
import com.iti.backend_challenge.application.useCases.impl.*
import com.iti.backend_challenge.domain.entities.Parameterization
import com.iti.backend_challenge.domain.entities.ParameterizationIntType
import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("PasswordValidator Use Cases Tests")
class PasswordValidatorUseCaseImplUnitTest {

    private fun createMockParameterizations() = listOf(
        ParameterizationIntType(
            id = "1",
            key = "MIN_CHARS",
            value = 9,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        ),
        ParameterizationIntType(
            id = "2",
            key = "MIN_DIGITS",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        ),
        ParameterizationIntType(
            id = "3",
            key = "MIN_LOWERCASE",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        ),
        ParameterizationIntType(
            id = "4",
            key = "MIN_UPPERCASE",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        ),
        ParameterizationIntType(
            id = "5",
            key = "MIN_SPECIAL_CHARS",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        ),
        ParameterizationListOfStringType(
            id = "6",
            key = "ALLOWED_SPECIAL_CHARS",
            value = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"),
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
    )

    // ==================== IsThePasswordIsNotEmptyUseCase ====================

    @DisplayName("IsThePasswordIsNotEmptyUseCase - Should return true for non-empty password")
    @Test
    fun testIsThePasswordIsNotEmptyUseCase_ValidPassword() {
        // Arrange
        val validator = IsThePasswordIsNotEmptyUseCase()
        val password = "MyPassword1!"
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsThePasswordIsNotEmptyUseCase - Should return false for empty password")
    @Test
    fun testIsThePasswordIsNotEmptyUseCase_EmptyPassword() {
        // Arrange
        val validator = IsThePasswordIsNotEmptyUseCase()
        val password = ""
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsThePasswordIsNotEmptyUseCase - Should return false for blank password")
    @Test
    fun testIsThePasswordIsNotEmptyUseCase_BlankPassword() {
        // Arrange
        val validator = IsThePasswordIsNotEmptyUseCase()
        val password = "   "
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    // ==================== IsTheCharacterCountValid ====================

    @DisplayName("IsTheCharacterCountValid - Should return true when password meets MIN_CHARS requirement")
    @Test
    fun testIsTheCharacterCountValid_ValidLength() {
        // Arrange
        val validator = IsTheCharacterCountValid()
        val password = "MyPassword1!" // 12 characters, min is 9
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheCharacterCountValid - Should return false when password is less than MIN_CHARS")
    @Test
    fun testIsTheCharacterCountValid_TooShort() {
        // Arrange
        val validator = IsTheCharacterCountValid()
        val password = "Short1!" // 7 characters, min is 9
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheCharacterCountValid - Should return true when password exactly matches MIN_CHARS")
    @Test
    fun testIsTheCharacterCountValid_ExactLength() {
        // Arrange
        val validator = IsTheCharacterCountValid()
        val password = "MyPass1!!" // exactly 9 characters
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheCharacterCountValid - Should throw exception when MIN_CHARS not found")
    @Test
    fun testIsTheCharacterCountValid_MissingParameter() {
        // Arrange
        val validator = IsTheCharacterCountValid()
        val password = "MyPassword1!"
        val parameters = emptyList<Parameterization>()

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    // ==================== IsTheQuantityOfNumbersValid ====================

    @DisplayName("IsTheQuantityOfNumbersValid - Should return true when password meets MIN_DIGITS requirement")
    @Test
    fun testIsTheQuantityOfNumbersValid_ValidDigits() {
        // Arrange
        val validator = IsTheQuantityOfNumbersValid()
        val password = "MyPassword123!" // has 3 digits, min is 1
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheQuantityOfNumbersValid - Should return false when password has no digits")
    @Test
    fun testIsTheQuantityOfNumbersValid_NoDigits() {
        // Arrange
        val validator = IsTheQuantityOfNumbersValid()
        val password = "MyPassword!" // no digits
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheQuantityOfNumbersValid - Should return true with exactly MIN_DIGITS digits")
    @Test
    fun testIsTheQuantityOfNumbersValid_ExactDigits() {
        // Arrange
        val validator = IsTheQuantityOfNumbersValid()
        val password = "MyPassword1!" // has exactly 1 digit
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheQuantityOfNumbersValid - Should throw exception when MIN_DIGITS not found")
    @Test
    fun testIsTheQuantityOfNumbersValid_MissingParameter() {
        // Arrange
        val validator = IsTheQuantityOfNumbersValid()
        val password = "MyPassword1!"
        val parameters = emptyList<Parameterization>()

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    // ==================== IsTheNumberOfUppercaseCharactersValid ====================

    @DisplayName("IsTheNumberOfUppercaseCharactersValid - Should return true when password meets MIN_UPPERCASE requirement")
    @Test
    fun testIsTheNumberOfUppercaseCharactersValid_ValidUppercase() {
        // Arrange
        val validator = IsTheNumberOfUppercaseCharactersValid()
        val password = "MyPassword1!" // has 2 uppercase, min is 1
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfUppercaseCharactersValid - Should return false when password has no uppercase")
    @Test
    fun testIsTheNumberOfUppercaseCharactersValid_NoUppercase() {
        // Arrange
        val validator = IsTheNumberOfUppercaseCharactersValid()
        val password = "mypassword1!" // no uppercase
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfUppercaseCharactersValid - Should return true with exactly MIN_UPPERCASE uppercase")
    @Test
    fun testIsTheNumberOfUppercaseCharactersValid_ExactUppercase() {
        // Arrange
        val validator = IsTheNumberOfUppercaseCharactersValid()
        val password = "myPassword1!" // has exactly 1 uppercase
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfUppercaseCharactersValid - Should throw exception when MIN_UPPERCASE not found")
    @Test
    fun testIsTheNumberOfUppercaseCharactersValid_MissingParameter() {
        // Arrange
        val validator = IsTheNumberOfUppercaseCharactersValid()
        val password = "MyPassword1!"
        val parameters = emptyList<Parameterization>()

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    // ==================== IsTheNumberOfLowercaseCharactersValid ====================

    @DisplayName("IsTheNumberOfLowercaseCharactersValid - Should return true when password meets MIN_LOWERCASE requirement")
    @Test
    fun testIsTheNumberOfLowercaseCharactersValid_ValidLowercase() {
        // Arrange
        val validator = IsTheNumberOfLowercaseCharactersValid()
        val password = "MyPassword1!" // has lowercase letters, min is 1
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfLowercaseCharactersValid - Should return false when password has no lowercase")
    @Test
    fun testIsTheNumberOfLowercaseCharactersValid_NoLowercase() {
        // Arrange
        val validator = IsTheNumberOfLowercaseCharactersValid()
        val password = "MYPASSWORD1!" // no lowercase
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfLowercaseCharactersValid - Should return true with exactly MIN_LOWERCASE lowercase")
    @Test
    fun testIsTheNumberOfLowercaseCharactersValid_ExactLowercase() {
        // Arrange
        val validator = IsTheNumberOfLowercaseCharactersValid()
        val password = "mYPASSWORD1!" // has exactly 1 lowercase
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfLowercaseCharactersValid - Should throw exception when MIN_LOWERCASE not found")
    @Test
    fun testIsTheNumberOfLowercaseCharactersValid_MissingParameter() {
        // Arrange
        val validator = IsTheNumberOfLowercaseCharactersValid()
        val password = "MyPassword1!"
        val parameters = emptyList<Parameterization>()

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    // ==================== IsTheNumberOfSpecialCharactersValid ====================

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should return true when password meets MIN_SPECIAL_CHARS requirement")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_ValidSpecialChars() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1!@" // has 2 special chars, min is 1
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should return false when password has no special chars")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_NoSpecialChars() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1" // no special chars
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should return true with exactly MIN_SPECIAL_CHARS special chars")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_ExactSpecialChars() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1!" // has exactly 1 special char
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should return false for disallowed special chars")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_DisallowedSpecialChars() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1~" // ~ is not in allowed special chars
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should throw exception when MIN_SPECIAL_CHARS not found")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_MissingMinParameter() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1!"
        val parameters = listOf(
            ParameterizationListOfStringType(
                id = "6",
                key = "ALLOWED_SPECIAL_CHARS",
                value = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"),
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )
        )

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    @DisplayName("IsTheNumberOfSpecialCharactersValid - Should throw exception when ALLOWED_SPECIAL_CHARS not found")
    @Test
    fun testIsTheNumberOfSpecialCharactersValid_MissingAllowedCharsParameter() {
        // Arrange
        val validator = IsTheNumberOfSpecialCharactersValid()
        val password = "MyPassword1!"
        val parameters = listOf(
            ParameterizationIntType(
                id = "5",
                key = "MIN_SPECIAL_CHARS",
                value = 1,
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )
        )

        // Act & Assert
        assertThrows<ParameterizationNotFoundException> {
            validator.isValidatePassword(password, parameters)
        }
    }

    // ==================== IsTheNumberOfRepeatedCharactersValid ====================

    @DisplayName("IsTheNumberOfRepeatedCharactersValid - Should return true when no characters are repeated")
    @Test
    fun testIsTheNumberOfRepeatedCharactersValid_NoRepeatedChars() {
        // Arrange
        val validator = IsTheNumberOfRepeatedCharactersValid()
        val password = "Pasword1!" // no repeated characters
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsTheNumberOfRepeatedCharactersValid - Should return false when characters are repeated")
    @Test
    fun testIsTheNumberOfRepeatedCharactersValid_WithRepeatedChars() {
        // Arrange
        val validator = IsTheNumberOfRepeatedCharactersValid()
        val password = "MyPassword11!" // has repeated 1s
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfRepeatedCharactersValid - Should return false when first and second chars are same")
    @Test
    fun testIsTheNumberOfRepeatedCharactersValid_RepeatedFirstChars() {
        // Arrange
        val validator = IsTheNumberOfRepeatedCharactersValid()
        val password = "MMPassword1!" // has repeated M
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsTheNumberOfRepeatedCharactersValid - Should return true for single character")
    @Test
    fun testIsTheNumberOfRepeatedCharactersValid_SingleChar() {
        // Arrange
        val validator = IsTheNumberOfRepeatedCharactersValid()
        val password = "A"
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    // ==================== IsThePasswordHasNoSpacesUseCase ====================

    @DisplayName("IsThePasswordHasNoSpacesUseCase - Should return true when password has no spaces")
    @Test
    fun testIsThePasswordHasNoSpacesUseCase_NoSpaces() {
        // Arrange
        val validator = IsThePasswordHasNoSpacesUseCase()
        val password = "MyPassword1!"
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }

    @DisplayName("IsThePasswordHasNoSpacesUseCase - Should return false when password has spaces")
    @Test
    fun testIsThePasswordHasNoSpacesUseCase_WithSpaces() {
        // Arrange
        val validator = IsThePasswordHasNoSpacesUseCase()
        val password = "My Password1!"
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsThePasswordHasNoSpacesUseCase - Should return false when password is only spaces")
    @Test
    fun testIsThePasswordHasNoSpacesUseCase_OnlySpaces() {
        // Arrange
        val validator = IsThePasswordHasNoSpacesUseCase()
        val password = "   "
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertFalse(result)
    }

    @DisplayName("IsThePasswordHasNoSpacesUseCase - Should return true for empty password")
    @Test
    fun testIsThePasswordHasNoSpacesUseCase_EmptyPassword() {
        // Arrange
        val validator = IsThePasswordHasNoSpacesUseCase()
        val password = ""
        val parameters = createMockParameterizations()

        // Act
        val result = validator.isValidatePassword(password, parameters)

        // Assert
        assertTrue(result)
    }
}

