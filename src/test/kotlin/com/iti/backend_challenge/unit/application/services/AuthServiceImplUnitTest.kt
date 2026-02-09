package com.iti.backend_challenge.unit.application.services

import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.exceptions.InternalServerErrorException
import com.iti.backend_challenge.application.services.impl.AuthServiceImpl
import com.iti.backend_challenge.application.useCases.impl.*
import com.iti.backend_challenge.domain.entities.ParameterizationIntType
import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
import com.iti.backend_challenge.domain.repositories.ParameterizationRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("AuthService Tests")
class AuthServiceImplUnitTest {

    @Mock
    private lateinit var parameterizationRepository: ParameterizationRepository

    private lateinit var authService: AuthServiceImpl

    private val validators = listOf(
        IsThePasswordIsNotEmptyUseCase(),
        IsTheCharacterCountValid(),
        IsTheQuantityOfNumbersValid(),
        IsTheNumberOfUppercaseCharactersValid(),
        IsTheNumberOfLowercaseCharactersValid(),
        IsTheNumberOfSpecialCharactersValid(),
        IsTheNumberOfRepeatedCharactersValid(),
        IsThePasswordHasNoSpacesUseCase()
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authService = AuthServiceImpl(parameterizationRepository, validators)
    }

    @Test
    @DisplayName("Should validate password successfully when all validators pass")
    fun shouldValidatePasswordSuccessfully() {
        // Arrange
        val validPassword = "Pasword1!"  // No repeated characters, 9 chars, 1 uppercase, lowercase, 1 digit, 1 special char
        val request = ValidatePasswordRequest(validPassword)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertTrue(response.isValid)
    }

    @Test
    @DisplayName("Should return false when password is too short")
    fun shouldReturnFalseWhenPasswordTooShort() {
        // Arrange
        val shortPassword = "Pass1!"
        val request = ValidatePasswordRequest(shortPassword)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid)
    }

    @Test
    @DisplayName("Should return false when password has no digits")
    fun shouldReturnFalseWhenNoDigits() {
        // Arrange
        val noDigitsPassword = "MyPassword!"
        val request = ValidatePasswordRequest(noDigitsPassword)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid)
    }

    @Test
    @DisplayName("Should return false when password is empty")
    fun shouldReturnFalseWhenEmpty() {
        // Arrange
        val emptyPassword = ""
        val request = ValidatePasswordRequest(emptyPassword)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid)
    }

    @Test
    @DisplayName("Should throw InternalServerErrorException when repository fails")
    fun shouldThrowExceptionWhenRepositoryFails() {
        // Arrange
        val validPassword = "MyPassword1!"
        val request = ValidatePasswordRequest(validPassword)

        whenever(parameterizationRepository.findAll()).thenThrow(RuntimeException("Database error"))

        // Act & Assert
        assertThrows<InternalServerErrorException> {
            authService.validatePassword(request)
        }
    }

    @Test
    @DisplayName("Should trim whitespace from password before validation")
    fun shouldTrimPasswordBeforeValidation() {
        // Arrange
        val passwordWithSpaces = "  Pasword1!  "  // No repeated characters when trimmed
        val request = ValidatePasswordRequest(passwordWithSpaces)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertTrue(response.isValid)
    }

    @Test
    @DisplayName("Should return false when password has spaces")
    fun shouldReturnFalseWhenHasSpaces() {
        // Arrange
        val passwordWithSpaces = "MyPassword 1!"
        val request = ValidatePasswordRequest(passwordWithSpaces)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid)
    }

    @Test
    @DisplayName("Should return false when password has repeated characters")
    fun shouldReturnFalseWhenRepeatedChars() {
        // Arrange
        val repeatedPassword = "MyPassword11!"
        val request = ValidatePasswordRequest(repeatedPassword)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid)
    }

    // ==================== Comprehensive Test Cases ====================

    @Test
    @DisplayName("Should return false for empty string")
    fun shouldReturnFalseForEmptyString() {
        // Arrange - IsValid("") // false
        val password = ""
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: IsThePasswordIsNotEmptyUseCase
    }

    @Test
    @DisplayName("Should return false for 'aa' - too short and has repeated characters")
    fun shouldReturnFalseForAa() {
        // Arrange - IsValid("aa") // false
        val password = "aa"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: too short (2 < 9), repeated 'a', no uppercase, no digit, no special char
    }

    @Test
    @DisplayName("Should return false for 'ab' - too short")
    fun shouldReturnFalseForAb() {
        // Arrange - IsValid("ab") // false
        val password = "ab"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: too short (2 < 9), no uppercase, no digit, no special char
    }

    @Test
    @DisplayName("Should return false for 'AAAbbbCc' - no digits and no special chars")
    fun shouldReturnFalseForAAAbbbCc() {
        // Arrange - IsValid("AAAbbbCc") // false
        val password = "AAAbbbCc"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: repeated 'A', 'b', 'C', no digit, no special char
    }

    @Test
    @DisplayName("Should return false for 'AbTp9!foo' - has repeated characters")
    fun shouldReturnFalseForAbTp9Foo() {
        // Arrange - IsValid("AbTp9!foo") // false
        val password = "AbTp9!foo"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: repeated 'o'
    }

    @Test
    @DisplayName("Should return false for 'AbTp9!foA' - has repeated characters")
    fun shouldReturnFalseForAbTp9FoA() {
        // Arrange - IsValid("AbTp9!foA") // false
        val password = "AbTp9!foA"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: repeated 'A'
    }

    @Test
    @DisplayName("Should return false for 'AbTp9 fok' - contains space")
    fun shouldReturnFalseForAbTp9SpaceFok() {
        // Arrange - IsValid("AbTp9 fok") // false
        val password = "AbTp9 fok"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertFalse(response.isValid) // Fails: contains space
    }

    @Test
    @DisplayName("Should return true for 'AbTp9!fok' - valid password")
    fun shouldReturnTrueForAbTp9Fok() {
        // Arrange - IsValid("AbTp9!fok") // true
        val password = "AbTp9!fok"
        val request = ValidatePasswordRequest(password)
        val parameterizations = createMockParameterizations()

        whenever(parameterizationRepository.findAll()).thenReturn(parameterizations)

        // Act
        val response = authService.validatePassword(request)

        // Assert
        assertTrue(response.isValid) // Passes all validators:
        // - Not empty ✓
        // - 9 chars ✓
        // - 1 digit (9) ✓
        // - 1 uppercase (A, T) ✓
        // - 1 lowercase (b, p, f, o, k) ✓
        // - 1 special char (!) ✓
        // - No repeated chars ✓
        // - No spaces ✓
    }

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
}

