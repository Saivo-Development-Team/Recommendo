package com.saivo.recommendo.service

import com.saivo.recommendo.lib.TwilioServer
import com.saivo.recommendo.model.domain.User
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
import kotlin.random.Random


@Service
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var encoder: PasswordEncoder

    fun saveUser(user: User, action: String = ""): Response {
        return when (action) {
            "register" -> registerUser(user)
            else -> saveNewUser(user)
        }
    }

    fun saveNewUser(user: User): Response {
        return Response().apply {
            user.apply {
                userRepository.save(encodeUserPassword { passwordEncoder(userPassword) }).apply {
                    data = id
                    status = "ADDED NEW USER"
                    message = "$firstname $lastname Registered with $email"
                }
            }
        }
    }

    fun registerUser(user: User): Response {
        return Response().apply {
            try {
                user.apply {
                    userRepository.save(encodeUserPassword { passwordEncoder(userPassword) }).apply {
                        data = id
                        status = "REGISTRATION_SUCCESSFUL"
                        message = "$firstname $lastname Registered with $email"
                    }
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
        return userRepository.findById(id).orElseThrow { UserNotFoundException() }

    }

    fun getUsers(): List<User> {
        return userRepository.findAll().toList().apply {
            if (isNullOrEmpty()) {
                throw EmptyResultException()
            }
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        return getUserByUsername(email)
    }

    fun getUserByUsername(email: String): User = userRepository
            .findUserByEmail(email).run {
                return this ?: throw UserNotFoundException()
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
        return encoder.matches(password, user.userPassword)
                .also {
                    if (!it) throw BadUserCredentialsException()
                }
    }

    fun passwordEncoder(password: String): String {
        return encoder.encode(password)
    }

    fun resetPassword(email: String, password: String) {
        saveUser(getUserByUsername(email).encodeUserPassword {
            passwordEncoder(password)
        })
    }

    fun sendUserSMS(email: String, number: String): String {
        var otp = ""
        for (i in 1..5) otp += Random.nextInt(1, 9)
        TwilioServer.sendSms("You're PIN is: $otp", number)
        return otp
    }
}
