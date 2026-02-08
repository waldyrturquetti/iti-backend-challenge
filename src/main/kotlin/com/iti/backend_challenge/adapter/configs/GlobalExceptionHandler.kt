package com.iti.backend_challenge.adapter.configs

import com.iti.backend_challenge.adapter.dtos.ErrorResponse
import com.iti.backend_challenge.adapter.exceptions.InternalServerErrorException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerError(exception: InternalServerErrorException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = exception.statusCode,
            message = exception.messageError ?: "Internal Server Error"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

