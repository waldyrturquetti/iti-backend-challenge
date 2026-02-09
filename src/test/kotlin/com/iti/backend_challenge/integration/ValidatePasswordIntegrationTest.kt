package com.iti.backend_challenge.integration

import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
import com.iti.backend_challenge.domain.entities.ParameterizationIntType
import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidatePasswordIntegrationTest {

    companion object {

        @Container
        @JvmStatic
        val mongo = MongoDBContainer("mongo:6.0")

        @JvmStatic
        @DynamicPropertySource
        fun mongoProps(registry: DynamicPropertyRegistry) {
            mongo.start()

            registry.add("spring.mongodb.uri", mongo::getReplicaSetUrl)
            registry.add("mongock.enabled") { false }
        }
    }

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @BeforeAll
    fun setup() {
        println("Mongo host: ${mongo.containerIpAddress}:${mongo.firstMappedPort}")

        mongoTemplate.dropCollection("parameterizations")

        val now = LocalDateTime.now()

        mongoTemplate.save(
            ParameterizationIntType(UUID.randomUUID().toString(), "MIN_CHARS", 9, now, null),
            "parameterizations"
        )
        mongoTemplate.save(
            ParameterizationIntType(UUID.randomUUID().toString(), "MIN_DIGITS", 1, now, null),
            "parameterizations"
        )
        mongoTemplate.save(
            ParameterizationIntType(UUID.randomUUID().toString(), "MIN_LOWERCASE", 1, now, null),
            "parameterizations"
        )
        mongoTemplate.save(
            ParameterizationIntType(UUID.randomUUID().toString(), "MIN_UPPERCASE", 1, now, null),
            "parameterizations"
        )
        mongoTemplate.save(
            ParameterizationIntType(UUID.randomUUID().toString(), "MIN_SPECIAL_CHARS", 1, now, null),
            "parameterizations"
        )
        mongoTemplate.save(
            ParameterizationListOfStringType(
                UUID.randomUUID().toString(),
                "ALLOWED_SPECIAL_CHARS",
                listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"),
                now,
                null
            ),
            "parameterizations"
        )
    }

    private fun post(password: String): ResponseEntity<Map<*, *>> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return restTemplate.postForEntity(
            "/auth/validate-password",
            HttpEntity(ValidatePasswordRequest(password), headers),
            Map::class.java
        )
    }

    @Test fun `valid password`() {
        val response = post("AbC123!@#")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(true, response.body!!["is_valid"])
    }

    @Test fun `too short`() {
        val response = post("Abc1!@")
        assertEquals(false, response.body!!["is_valid"])
    }

    @Test fun `no digits`() {
        val response = post("AbcDefGh!@")
        assertEquals(false, response.body!!["is_valid"])
    }

    @Test fun `no lowercase`() {
        val response = post("ABC123!@#")
        assertEquals(false, response.body!!["is_valid"])
    }

    @Test fun `no uppercase`() {
        val response = post("abc123!@#")
        assertEquals(false, response.body!!["is_valid"])
    }

    @Test fun `no special chars`() {
        val response = post("AbcDef1234")
        assertEquals(false, response.body!!["is_valid"])
    }

    @Test fun `disallowed special chars`() {
        val response = post("AbC123&[]")
        assertEquals(true, response.body!!["is_valid"])
    }

    @Test fun `blank password`() {
        val response = post("")
        assertEquals(false, response.body!!["is_valid"])
    }
}
