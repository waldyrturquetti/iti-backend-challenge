package com.iti.backend_challenge.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document as MongoDocument
import java.time.LocalDateTime

@MongoDocument(collection = "parameterizations")
sealed class Parameterization(
    @field:Id
    open val Id: String,
    open val DocumentType: String,
    open val Value: Any,
    open val CreatedAt: LocalDateTime = LocalDateTime.now(),
    open val UpdatedAt: LocalDateTime? = null,
)


@TypeAlias("int_parameter")
@MongoDocument(collection = "parameterizations")
data class ParameterizationIntType(
    @field:Id
    override val Id: String,
    override val DocumentType: String = "INT",
    override val Value: Int,
    override val CreatedAt: LocalDateTime = LocalDateTime.now(),
    override val UpdatedAt: LocalDateTime = LocalDateTime.now(),
) : Parameterization(Id, DocumentType, Value,  CreatedAt, UpdatedAt)


@TypeAlias("string_list_parameter")
@MongoDocument(collection = "parameterizations")
data class ParameterizationListOfStringType(
    @field:Id
    override val Id: String,
    override val DocumentType: String = "LIST_OF_STRING",
    override val Value: List<String>,
    override val CreatedAt: LocalDateTime = LocalDateTime.now(),
    override val UpdatedAt: LocalDateTime = LocalDateTime.now(),
) : Parameterization(Id, DocumentType, Value,  CreatedAt, UpdatedAt)