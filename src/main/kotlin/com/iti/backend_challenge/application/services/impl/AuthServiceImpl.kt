package com.iti.backend_challenge.application.services.impl

import com.iti.backend_challenge.application.services.IAuthService
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordResponse
import com.iti.backend_challenge.adapter.exceptions.InternalServerErrorException
import com.iti.backend_challenge.application.helpers.PasswordValidator
import com.iti.backend_challenge.domain.repositories.ParameterizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    @Autowired val parameterizationRepository: ParameterizationRepository,
    @Autowired val passwordValidators: List<PasswordValidator>
) : IAuthService {

    override fun validatePassword(validatePasswordRequest: ValidatePasswordRequest): ValidatePasswordResponse {

        try {
            val parameterizations = parameterizationRepository.findAll()

            val isValid = passwordValidators.all { validator ->
                validator.isValidatePassword(validatePasswordRequest.password, parameterizations)
            }

            return ValidatePasswordResponse(isValid)
        } catch (ex: Exception) {
            throw InternalServerErrorException(messageError = ex.message)
        }
    }
}