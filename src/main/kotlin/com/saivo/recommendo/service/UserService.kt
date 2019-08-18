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
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.stereotype.Service


@Service
class UserService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    @Autowired
    private val tokenStore: TokenStore? = null

    fun saveUser(user: User, action: String? = "save"): Response {
        userRepository!!.save(user.apply {
            password = encoder!!.encode(password)
        }).also {
            return Response(data = getUserByUsername(it.email).id, status = "REGISTRATION_SUCCESSFUL")
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

    fun loginUser(login: Login): Response { // Send with Access Token
        val response = Response()
        try {
            response.data = getUserByUsername(login.email)
            if(checkUserPassword(response.data as User, login.password)){
                response.status = "LOGIN_SUCCESSFUL"
                println(message = response.status)
            } else {
                response.status = "LOGIN_UNSUCCESSFUL"
                response.data = null
            }
        } catch (e : Exception) {
            response.status = "LOGIN_UNSUCCESSFUL"
            when(e){
                is UserNotFoundException -> {
                    response.error = "USER_NOT_FOUND"
                    response.message = "Try Registering before Login"
                }
                is BadUserCredentialsException -> {
                    response.error  = "BAD_USER_CREDENTIALS"
                    response.message = "Seems like your Password and Email Details are invalid"
                }
            }
            response.data = null
        }
        return response
    }

    fun checkUserPassword(user: User, password: String): Boolean {
        return when {
            encoder!!.matches(password, user.password) -> true
            else -> throw BadUserCredentialsException()
        }
    }
}
