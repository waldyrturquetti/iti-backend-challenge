package com.iti.backend_challenge.application.services

import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordResponse

interface IAuthService {
    fun validatePassword(validatePasswordRequest: ValidatePasswordRequest): ValidatePasswordResponse
}