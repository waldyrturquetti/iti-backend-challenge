package com.iti.backend_challenge.adapter.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nonnull

data class ErrorResponse(

    @field:Nonnull
    @JsonProperty("status", required = true)
    val status: Int,

    @field:Nonnull
    @JsonProperty("message", required = true)
    val message: String,

    @JsonProperty("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

