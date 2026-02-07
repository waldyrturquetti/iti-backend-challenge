package com.iti.backend_challenge.controllers

import com.iti.backend_challenge.entities.*
import com.iti.backend_challenge.services.DocumentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth")
class AuthController(private val documentService: DocumentService) {

    @PostMapping("/validate-password")
    fun validatePassword(@RequestBody request: TextDocumentRequest): ResponseEntity<Boolean> {
        val doc = TextDocument(
            title = request.title,
            content = request.content,
            author = request.author,
            tags = request.tags ?: emptyList(),
            metadata = request.metadata
        )
        val saved = documentService.saveDocument(doc)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }
}

