package com.iti.backend_challenge.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document as MongoDocument
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@MongoDocument(collection = "parameterizations")
sealed class Parameterization(
    @field:Id
    open val id: String,

    @Indexed(unique = true)
    @field:Field("key")
    open val key: String,

    @field:Field("value")
    open val value: Any,

    @field:Field("created_at")
    open val createdAt: LocalDateTime = LocalDateTime.now(),

    @field:Field("updated_at")
    open val updatedAt: LocalDateTime? = null,
)


@TypeAlias("INT_PARAMETER")
class ParameterizationIntType(
    id: String,
    key: String,
    value: Int,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime? = null,
) : Parameterization(id, key, value, createdAt, updatedAt)


@TypeAlias("STRING_LIST_PARAMETER")
class ParameterizationListOfStringType(
    id: String,
    key: String,
    value: List<String>,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime? = null,
) : Parameterization(id, key, value, createdAt, updatedAt)
