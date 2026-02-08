package com.iti.backend_challenge.adapter.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nonnull

data class HealthResponse(

    @field:Nonnull
    @JsonProperty("status", required = true)
    val status: String,
)