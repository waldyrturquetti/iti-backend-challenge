package com.iti.backend_challenge.repositories

import com.iti.backend_challenge.entities.Parameterization
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ParameterizationRepository : MongoRepository<Parameterization, String> {

    override fun findAll(): List<Parameterization>
}

