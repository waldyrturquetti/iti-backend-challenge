package com.iti.backend_challenge.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document as MongoDocument
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@MongoDocument(collection = "parameterizations")
sealed class Parameterization(
    @field:Id
    open val id: String,
    @field:Field("DocumentType")
    open val documentType: String,
    @field:Field("Value")
    open val value: Any,
    @field:Field("CreatedAt")
    open val createdAt: LocalDateTime = LocalDateTime.now(),
    @field:Field("UpdatedAt")
    open val updatedAt: LocalDateTime? = null,
)


@TypeAlias("int_parameter")
data class ParameterizationIntType(
    override val id: String,
    override val value: Int,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime? = null,
) : Parameterization(id, "INT", value, createdAt, updatedAt)


@TypeAlias("string_list_parameter")
data class ParameterizationListOfStringType(
    override val id: String,
    override val value: List<String>,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime? = null,
) : Parameterization(id, "LIST_OF_STRING", value, createdAt, updatedAt)
