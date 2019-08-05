package com.saivo.recommendo.repository

import com.saivo.recommendo.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, String>{
    fun findUserByUsername(username: String): User?
}