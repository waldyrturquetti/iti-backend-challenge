//package com.iti.backend_challenge.integration
//
//import com.iti.backend_challenge.adapter.dtos.ValidatePasswordRequest
//import com.iti.backend_challenge.domain.entities.ParameterizationIntType
//import com.iti.backend_challenge.domain.entities.ParameterizationListOfStringType
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.resttestclient.TestRestTemplate
//import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpStatus
//import org.springframework.http.MediaType
//import org.springframework.test.context.ActiveProfiles
//import java.time.LocalDateTime
//import java.util.UUID
//import kotlin.test.assertEquals
//import kotlin.test.assertNotNull
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestRestTemplate
//@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class ValidatePasswordIntegrationTest {
//
//
//    @Autowired
//    private lateinit var restTemplate: TestRestTemplate
//
//    @Autowired
//    private lateinit var mongoTemplate: MongoTemplate
//
//    @BeforeAll
//    fun setup() {
//        // Drop collection if exists
//        if (mongoTemplate.collectionExists("parameterizations")) {
//            mongoTemplate.dropCollection("parameterizations")
//        }
//
//        // Create collection and seed test data
//        mongoTemplate.createCollection("parameterizations")
//
//        val minChars = ParameterizationIntType(
//            id = UUID.randomUUID().toString(),
//            key = "MIN_CHARS",
//            value = 9,
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(minChars, "parameterizations")
//
//        val minDigits = ParameterizationIntType(
//            id = UUID.randomUUID().toString(),
//            key = "MIN_DIGITS",
//            value = 1,
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(minDigits, "parameterizations")
//
//        val minLowercase = ParameterizationIntType(
//            id = UUID.randomUUID().toString(),
//            key = "MIN_LOWERCASE",
//            value = 1,
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(minLowercase, "parameterizations")
//
//        val minUppercase = ParameterizationIntType(
//            id = UUID.randomUUID().toString(),
//            key = "MIN_UPPERCASE",
//            value = 1,
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(minUppercase, "parameterizations")
//
//        val minSpecialChars = ParameterizationIntType(
//            id = UUID.randomUUID().toString(),
//            key = "MIN_SPECIAL_CHARS",
//            value = 1,
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(minSpecialChars, "parameterizations")
//
//        val allowedSpecialChars = ParameterizationListOfStringType(
//            id = UUID.randomUUID().toString(),
//            key = "ALLOWED_SPECIAL_CHARS",
//            value = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"),
//            createdAt = LocalDateTime.now(),
//            updatedAt = null
//        )
//        mongoTemplate.save(allowedSpecialChars, "parameterizations")
//    }
//
//    @Test
//    fun `should return valid when password meets all requirements`() {
//        // Arrange
//        val validPassword = "AbC123!@#"
//        val request = ValidatePasswordRequest(password = validPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(true, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password is too short`() {
//        // Arrange
//        val shortPassword = "Abc1!@"
//        val request = ValidatePasswordRequest(password = shortPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password has no digits`() {
//        // Arrange
//        val noDigitPassword = "AbcDefGh!@"
//        val request = ValidatePasswordRequest(password = noDigitPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password has no lowercase letters`() {
//        // Arrange
//        val noLowercasePassword = "ABC123!@#"
//        val request = ValidatePasswordRequest(password = noLowercasePassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password has no uppercase letters`() {
//        // Arrange
//        val noUppercasePassword = "abc123!@#"
//        val request = ValidatePasswordRequest(password = noUppercasePassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password has no special characters`() {
//        // Arrange
//        val noSpecialCharPassword = "AbcDef1234"
//        val request = ValidatePasswordRequest(password = noSpecialCharPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return invalid when password contains disallowed special characters`() {
//        // Arrange
//        val disallowedSpecialCharPassword = "AbC123&[]"
//        val request = ValidatePasswordRequest(password = disallowedSpecialCharPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(false, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return bad request when password is blank`() {
//        // Arrange
//        val blankPassword = ""
//        val request = ValidatePasswordRequest(password = blankPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//    }
//
//    @Test
//    fun `should return valid for complex valid password`() {
//        // Arrange
//        val complexPassword = "MyP@sw0rd123!"
//        val request = ValidatePasswordRequest(password = complexPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(true, response.body!!["is_valid"])
//    }
//
//    @Test
//    fun `should return valid for password with multiple special characters`() {
//        // Arrange
//        val multiSpecialCharPassword = "Test123!@#$%"
//        val request = ValidatePasswordRequest(password = multiSpecialCharPassword)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val entity = HttpEntity(request, headers)
//
//        // Act
//        val response = restTemplate.postForEntity(
//            "/auth/validate-password",
//            entity,
//            Map::class.java
//        )
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(true, response.body!!["is_valid"])
//    }
//}
