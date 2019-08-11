package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.model.infrastructure.AuthUser
import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.repository.UserRepository
import com.saivo.recommendo.util.exception.BadUserCredentialsException
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

    fun saveUser(user: User, action: String? = "save"): String {
        userRepository!!.save(user.apply {
            password = encoder!!.encode(password)
        }).also {
            return getUserByUsername(it.email).id
        }
    }

    fun getUserById(id: String): User {
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

    override fun loadUserByUsername(email: String): UserDetails {
        return UserDetailsService {
            AuthUser(getUserByUsername(email))
        }.loadUserByUsername(email)
    }

    fun getUserByUsername(email: String): User {
        userRepository!!.findUserByEmail(email).let {
            return when {
                it != null -> it
                else -> throw UserNotFoundException()
            }
        }
    }

    fun loginUser(login: Login): User { // Send with Access Token
        getUserByUsername(login.email).let {
            return when {
                encoder!!.matches(login.password, it.password) -> it
                else -> throw BadUserCredentialsException()
            }
        }
    }

//    fun isUser(Id: String, email: String = ""): Boolean {
//        try {
//            return when {
//                getUserById(Id).id!!.isNotEmpty() -> true
//                email.isNotBlank() -> getUserByUsername(email).id!!.isNotEmpty()
//                else -> Id == getUserByUsername(email).id
//            }
//        } catch (e: UserNotFoundException) {
//            println(e)
//        }
//        return false
//    }

//    fun deleteUser(id: String) {
//        userRepository!!.deleteById(id)
//    }
}
