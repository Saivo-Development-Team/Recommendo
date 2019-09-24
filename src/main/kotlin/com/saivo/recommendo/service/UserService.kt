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


@Service
class UserService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    fun saveUser(user: User, action: String = ""): Response {
        return when (action) {
            "register" -> registerUser(user)
            else -> saveNewUser(user)
        }
    }

    fun saveNewUser(user: User): Response {
        return Response().apply {
            userRepository
                    ?.save(user.encodeUserPassword { passwordEncoder(it) })
                    ?.apply {
                        data = id
                        status = "ADDED NEW USER"
                        message = "$firstname $lastname Registered with $email"
                    }
        }
    }

    fun registerUser(user: User): Response {
        return Response().apply {
            try {
                userRepository
                        ?.save(user.encodeUserPassword { passwordEncoder(it) })
                        ?.apply {
                            data = id
                            status = "REGISTRATION_SUCCESSFUL"
                            message = "$firstname $lastname Registered with $email"
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
        return when {
            userRepository != null -> {
                return userRepository
                        .findById(id)
                        .orElseThrow { UserNotFoundException() }
            }
            else -> Any() as User
        }
    }

    fun getUsers(): List<User> {
        return when {
            userRepository != null -> return userRepository
                    .findAll()
                    .toList().apply {
                        if (isNullOrEmpty()) {
                            throw EmptyResultException()
                        }
                    }
            else -> emptyList()
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        return AuthUser(getUserByUsername(email))
    }

    fun getUserByUsername(email: String): User = userRepository
            ?.findUserByEmail(email).run {
                return when {
                    this != null -> this
                    else -> throw UserNotFoundException()
                }
            }

    fun loginUser(login: Login): Response {
        return Response().apply {
            try {
                getUserByUsername(login.email).run {
                    if (checkUserPassword(this, login.password)) {
                        data = this
                        status = "LOGIN_SUCCESSFUL"
                        message = "$firstname $lastname has Logged In"
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
            encoder != null -> encoder.matches(password, user.password)
                    .also {
                        if (!it) throw BadUserCredentialsException()
                    }
            else -> false
        }
    }

    fun passwordEncoder(password: String): String {
        return when {
            encoder != null -> encoder.encode(password)
            else -> password
        }
    }
}
