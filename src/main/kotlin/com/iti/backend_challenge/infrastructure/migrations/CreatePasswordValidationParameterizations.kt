package com.iti.backend_challenge.infrastructure.migrations

import com.iti.backend_challenge.domain.entities.ParameterizationIntType
import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime
import java.util.UUID

@ChangeUnit(id = "create-password-validation-parameterizations", order = "001", author = "system")
class CreatePasswordValidationParameterizations {

    @Execution
    fun execution(mongoTemplate: MongoTemplate) {

        if (!mongoTemplate.collectionExists("parameterizations")) {
            mongoTemplate.createCollection("parameterizations")
        }

        val minChars = ParameterizationIntType(
            id = UUID.randomUUID().toString(),
            key = "MIN_CHARS",
            value = 9,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(minChars, "parameterizations")

        val minDigits = ParameterizationIntType(
            id = UUID.randomUUID().toString(),
            key = "MIN_DIGITS",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(minDigits, "parameterizations")

        val minLowercase = ParameterizationIntType(
            id = UUID.randomUUID().toString(),
            key = "MIN_LOWERCASE",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(minLowercase, "parameterizations")

        val minUppercase = ParameterizationIntType(
            id = UUID.randomUUID().toString(),
            key = "MIN_UPPERCASE",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(minUppercase, "parameterizations")

        val minSpecialChars = ParameterizationIntType(
            id = UUID.randomUUID().toString(),
            key = "MIN_SPECIAL_CHARS",
            value = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(minSpecialChars, "parameterizations")

        val allowedSpecialChars = ParameterizationListOfStringType(
            id = UUID.randomUUID().toString(),
            key = "ALLOWED_SPECIAL_CHARS",
            value = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"),
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
        mongoTemplate.save(allowedSpecialChars, "parameterizations")
    }

    @RollbackExecution
    fun rollback(mongoTemplate: MongoTemplate) {
        mongoTemplate.dropCollection("parameterizations")
    }
}

