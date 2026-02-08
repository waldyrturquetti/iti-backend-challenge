package com.iti.backend_challenge.adapter.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidatePasswordResponse (

    @JsonProperty("is_valid", required = true)
    val isValid: Boolean = false
)