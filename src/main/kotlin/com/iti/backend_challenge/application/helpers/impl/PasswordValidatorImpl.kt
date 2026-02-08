package com.iti.backend_challenge.application.helpers.impl

import com.iti.backend_challenge.application.exceptions.ParameterizationNotFoundException
import com.iti.backend_challenge.application.helpers.PasswordValidator
import com.iti.backend_challenge.domain.entities.Parameterization
import com.iti.backend_challenge.domain.entities.ParameterizationIntType
import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
import org.springframework.stereotype.Component


@Component
class IsThePasswordIsNotEmpty : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        return password.isNotEmpty()
    }
}

@Component
class IsTheCharacterCountValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        val minChars = parameters
            .filterIsInstance<ParameterizationIntType>()
            .find { it.key == "MIN_CHARS" }
            ?.value as? Int ?: throw ParameterizationNotFoundException("MIN_CHARS")

        return password.length >= minChars
    }
}

@Component
class IsTheQuantityOfNumbersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        val minDigits = parameters
            .filterIsInstance<ParameterizationIntType>()
            .find { it.key == "MIN_DIGITS" }
            ?.value as? Int ?: throw ParameterizationNotFoundException("MIN_DIGITS")

        val digitCount = password.count { it.isDigit() }
        return digitCount >= minDigits
    }
}

@Component
class IsTheNumberOfUppercaseCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        val minUppercase = parameters
            .filterIsInstance<ParameterizationIntType>()
            .find { it.key == "MIN_UPPERCASE" }
            ?.value as? Int ?: throw ParameterizationNotFoundException("MIN_UPPERCASE")

        val uppercaseCount = password.count { it.isUpperCase() }
        return uppercaseCount >= minUppercase
    }
}

@Component
class IsTheNumberOfLowercaseCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        val minLowercase = parameters
            .filterIsInstance<ParameterizationIntType>()
            .find { it.key == "MIN_LOWERCASE" }
            ?.value as? Int ?: throw ParameterizationNotFoundException("MIN_LOWERCASE")

        val lowercaseCount = password.count { it.isLowerCase() }
        return lowercaseCount >= minLowercase
    }
}

@Component
class IsTheNumberOfSpecialCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        val minSpecialChars = parameters
            .filterIsInstance<ParameterizationIntType>()
            .find { it.key == "MIN_SPECIAL_CHARS" }
            ?.value as? Int ?: throw ParameterizationNotFoundException("MIN_SPECIAL_CHARS")

        @Suppress("UNCHECKED_CAST")
        val allowedSpecialChars = (parameters
            .filterIsInstance<ParameterizationListOfStringType>()
            .find { it.key == "ALLOWED_SPECIAL_CHARS" }
            ?.value as? List<String>) ?: throw ParameterizationNotFoundException("ALLOWED_SPECIAL_CHARS")

        val specialCharCount = password.count { char ->
            allowedSpecialChars.any { it.firstOrNull() == char }
        }

        return specialCharCount >= minSpecialChars
    }
}

@Component
class IsTheNumberOfRepeatedCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
        if (password.length < 2) return false

        var consecutiveCount = 1
        for (i in 1 until password.length) {
            if (password[i] == password[i - 1]) {
                consecutiveCount++
                if (consecutiveCount > 2) {
                    return false
                }
            } else {
                consecutiveCount = 1
            }
        }

        return true
    }
}