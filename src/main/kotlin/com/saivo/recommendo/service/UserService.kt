package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.model.infrastructure.AuthUser
import com.saivo.recommendo.repository.UserRepository
import com.saivo.recommendo.util.exception.EmptyResultException
import com.saivo.recommendo.util.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    fun addUser(user: User): String {
        userRepository!!.save(user.apply {
            this.password = encoder!!.encode(user.password)
        }).also {
            return getUserByUsername(it.username).id!!
        }
    }

    fun getUser(id: String): User {
        return userRepository!!.findById(id).orElseThrow {
            UserNotFoundException()
        }
    }

    fun getUsers(): List<User> {
        return userRepository!!.findAll().toList().also {
            if (it.isNullOrEmpty()) {
                throw EmptyResultException()
            }
        }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return UserDetailsService {
            AuthUser(userRepository!!.findUserByUsername(username!!)!!)
        }.loadUserByUsername(username)
    }

    fun getUserByUsername(username: String?): User {
        return userRepository!!.findUserByUsername(username!!).also {
            if (it!!.id.isNullOrEmpty()) {
                throw UserNotFoundException()
            }
        }!!
    }

    fun deleteUser(id: String) {
        userRepository!!.deleteById(id)
    }
}
