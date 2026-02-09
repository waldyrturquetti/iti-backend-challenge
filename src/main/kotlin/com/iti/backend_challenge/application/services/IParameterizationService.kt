package com.iti.backend_challenge.application.services

import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordResponse
import com.iti.backend_challenge.domain.entities.Parameterization

interface IParameterizationService {
    fun getAllParameterizations(): List<Parameterization>
}