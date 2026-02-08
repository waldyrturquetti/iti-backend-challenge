package com.iti.backend_challenge.adapter.controllers

import com.iti.backend_challenge.adapter.dtos.HealthResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
@Tag(name = "Health", description = "Health check endpoints")
class HealthController {

    @Operation(summary = "Health check", description = "Returns the health status of the application")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Application is healthy",
                content = [Content(schema = Schema(implementation = HealthResponse::class))])
        ]
    )
    @GetMapping("/health-check")
    fun health(): ResponseEntity<HealthResponse> {
        return ResponseEntity.ok(HealthResponse("OK"))
    }
}

