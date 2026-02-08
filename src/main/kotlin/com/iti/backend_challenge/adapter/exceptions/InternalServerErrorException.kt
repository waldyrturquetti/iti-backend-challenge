package com.iti.backend_challenge.adapter.exceptions

class InternalServerErrorException(
    val statusCode: Int = 500,
    val messageError: String? = "Internal Server Error"
) : RuntimeException(messageError)

