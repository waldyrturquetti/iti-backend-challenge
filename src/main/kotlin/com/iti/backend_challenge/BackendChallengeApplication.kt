package com.iti.backend_challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendChallengeApplication

fun main(args: Array<String>) {
	runApplication<BackendChallengeApplication>(*args)
	println("Access the API documentation at: http://localhost:8080/swagger-ui/index.html")
}
