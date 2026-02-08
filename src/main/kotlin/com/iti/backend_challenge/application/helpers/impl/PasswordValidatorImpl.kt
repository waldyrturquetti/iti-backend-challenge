package com.iti.backend_challenge.application.helpers.impl

import com.iti.backend_challenge.application.helpers.PasswordValidator
import com.iti.backend_challenge.domain.entities.Parameterization
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
//        TODO("Not yet implemented")
        return true
    }
}


@Component
class IsTheQuantityOfNumbersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
//        TODO("Not yet implemented")
        return true
    }
}

@Component
class IsTheNumberOfUppercaseCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
//        TODO("Not yet implemented")
        return true
    }
}

@Component
class IsTheNumberOfLowercaseCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
//        TODO("Not yet implemented")
        return false
    }
}

@Component
class IsTheNumberOfSpecialCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
//        TODO("Not yet implemented")
        return true
    }
}

@Component
class IsTheNumberOfRepeatedCharactersValid : PasswordValidator {

    override fun isValidatePassword(
        password: String,
        parameters: List<Parameterization>
    ): Boolean {
//        TODO("Not yet implemented")
        return true
    }
}