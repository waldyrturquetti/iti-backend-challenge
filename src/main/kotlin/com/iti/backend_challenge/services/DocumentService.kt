package com.iti.backend_challenge.services

import com.iti.backend_challenge.entities.*
import com.iti.backend_challenge.repositories.ParameterizationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Service for managing different types of documents in MongoDB.
 */
@Service
class DocumentService(private val parameterizationRepository: ParameterizationRepository) {

    /**
     * Save any type of document to the database
     */
    fun saveDocument(document: Document): Document {
        return parameterizationRepository.save(document)
    }

    /**
     * Find all documents
     */
    fun findAllDocuments(): List<Document> {
        return parameterizationRepository.findAll()
    }

    /**
     * Find document by ID
     */
    fun findDocumentById(id: String): Document? {
        return parameterizationRepository.findById(id).orElse(null)
    }

    /**
     * Find documents by type
     */
    fun findDocumentsByType(type: String): List<Document> {
        return parameterizationRepository.findByDocumentType(type)
    }

    /**
     * Delete document by ID
     */
    fun deleteDocument(id: String) {
        parameterizationRepository.deleteById(id)
    }

    /**
     * Update document
     */
    fun updateDocument(id: String, updatedDocument: Document): Document? {
        if (!parameterizationRepository.existsById(id)) {
            return null
        }
        return parameterizationRepository.save(updatedDocument)
    }

    /**
     * Get all text documents
     */
    fun getAllTextDocuments(): List<Document> {
        return parameterizationRepository.findByDocumentType("TEXT")
    }

    /**
     * Get all JSON documents
     */
    fun getAllJsonDocuments(): List<Document> {
        return parameterizationRepository.findByDocumentType("JSON")
    }

    /**
     * Get all file documents
     */
    fun getAllFileDocuments(): List<Document> {
        return parameterizationRepository.findByDocumentType("FILE")
    }

    /**
     * Get documents created after a specific date
     */
    fun getDocumentsCreatedAfter(date: LocalDateTime): List<Document> {
        return parameterizationRepository.findByCreatedAtAfter(date)
    }
}

