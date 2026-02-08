package com.iti.backend_challenge.adapter.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ValidatePasswordRequest(
    @field:NotBlank
    @JsonProperty("password", required = true)
    val password: String
)
