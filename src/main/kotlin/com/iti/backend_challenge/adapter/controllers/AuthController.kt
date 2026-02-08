package com.iti.backend_challenge.adapter.controllers

import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.adapter.dtos.ValidatePasswordResponse
import com.iti.backend_challenge.application.services.IAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
class AuthController(@Autowired val authService: IAuthService) {

    @Operation(summary = "Validate Password", description = "Returns whether the provided password is valid according to the defined rules")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Password validation result",
                content = [Content(schema = Schema(implementation = ValidatePasswordResponse::class))])
        ]
    )
    @PostMapping("/validate-password")
    fun validatePassword(@RequestBody request: ValidatePasswordRequest): ResponseEntity<ValidatePasswordResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                authService.validatePassword(request)
            )
    }
}

