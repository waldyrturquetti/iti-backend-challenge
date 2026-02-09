package com.iti.backend_challenge.application.services.impl

import com.iti.backend_challenge.adapter.exceptions.InternalServerErrorException
import com.iti.backend_challenge.application.services.IParameterizationService
import com.iti.backend_challenge.domain.repositories.ParameterizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ParameterizationServiceImpl(
    @Autowired val parameterizationRepository: ParameterizationRepository,
) : IParameterizationService {

    override fun getAllParameterizations() = try {
        parameterizationRepository.findAll()
    } catch (e: Exception) {
        throw InternalServerErrorException(messageError = "Failed to retrieve parameterizations")
    }
}