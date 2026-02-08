package com.iti.backend_challenge.application.exceptions

class ParameterizationNotFoundException(parameterKey: String) :
    RuntimeException("Required parameterization not found: $parameterKey")

