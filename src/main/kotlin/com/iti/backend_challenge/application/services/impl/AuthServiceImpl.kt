package com.iti.backend_challenge.application.services.impl

import com.iti.backend_challenge.application.services.IAuthService
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordResponse
import com.iti.backend_challenge.adapter.exceptions.InternalServerErrorException
import com.iti.backend_challenge.application.useCases.PasswordValidatorUseCase
import com.iti.backend_challenge.domain.entities.Parameterization
import com.iti.backend_challenge.domain.repositories.ParameterizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    @Autowired val passwordValidatorsUseCase: List<PasswordValidatorUseCase>
) : IAuthService {

    override fun validatePassword(validatePasswordRequest: ValidatePasswordRequest,
                                  parameterizations: List<Parameterization>
    ): ValidatePasswordResponse {

        try {
            val isValid = passwordValidatorsUseCase.all { validator ->
                validator.isValidatePassword(validatePasswordRequest.password.trim(), parameterizations)
            }
            return ValidatePasswordResponse(isValid)
        } catch (ex: Exception) {
            throw InternalServerErrorException(messageError = ex.message)
        }
    }
}