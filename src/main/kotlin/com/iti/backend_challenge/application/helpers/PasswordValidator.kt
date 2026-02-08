package com.iti.backend_challenge.application.helpers

import com.iti.backend_challenge.domain.entities.Parameterization

interface PasswordValidator {
    fun isValidatePassword(password: String, parameters: List<Parameterization>): Boolean
}