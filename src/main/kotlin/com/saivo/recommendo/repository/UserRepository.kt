package com.saivo.recommendo.repository

import com.saivo.recommendo.model.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, String>{
    fun findUserByEmail(email: String): User?
}