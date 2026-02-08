package com.iti.backend_challenge.domain.repositories

import com.iti.backend_challenge.domain.entities.Parameterization
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ParameterizationRepository : MongoRepository<Parameterization, String> {

    override fun findAll(): List<Parameterization>
}

