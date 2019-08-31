package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.model.infrastructure.AuthUser
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.repository.UserRepository
import com.saivo.recommendo.util.exception.BadUserCredentialsException
import com.saivo.recommendo.util.exception.EmptyResultException
import com.saivo.recommendo.util.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.validation.ConstraintViolationException


@Service
class UserService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    fun saveUser(user: User, action: String? = "save"): Response {
        return Response().apply {
            try {
                userRepository!!.save(user.apply {
                    password = encoder!!.encode(password)
                }).also {
                    data = it.id
                    status = "REGISTRATION_SUCCESSFUL"
                    message = "${it.firstname} ${it.lastname} Registered with ${it.email}"
                }
            } catch (e: Exception) {
                status = "REGISTRATION_UNSUCCESSFUL"
                when (e) {
                    is DataIntegrityViolationException -> {
                        error = "EMAIL_TAKEN"
                        message = "Can't Register more accounts"
                    }
                }
            }
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

    fun loginUser(login: Login): Response {
        return Response().apply {
            try {
                getUserByUsername(login.email).let {
                    if (checkUserPassword(it, login.password)) {
                        data = it
                        status = "LOGIN_SUCCESSFUL"
                        message = "${it.firstname} ${it.lastname} has Logged In"
                    }
                }
            } catch (e: Exception) {
                status = "LOGIN_UNSUCCESSFUL"
                when (e) {
                    is UserNotFoundException -> {
                        error = "USER_NOT_FOUND"
                        message = "Try Registering"
                    }
                    is BadUserCredentialsException -> {
                        error = "BAD_USER_CREDENTIALS"
                        message = "Invalid Password or Email"
                    }
                }
            }
        }
    }

    fun checkUserPassword(user: User, password: String): Boolean {
        return when {
            encoder!!.matches(password, user.password) -> true
            else -> throw BadUserCredentialsException()
        }
    }
}
