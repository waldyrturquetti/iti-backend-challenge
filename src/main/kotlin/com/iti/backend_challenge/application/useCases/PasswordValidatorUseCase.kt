package com.iti.backend_challenge.application.useCases

import com.iti.backend_challenge.domain.entities.Parameterization

interface PasswordValidatorUseCase {
    fun isValidatePassword(password: String, parameters: List<Parameterization>): Boolean
}