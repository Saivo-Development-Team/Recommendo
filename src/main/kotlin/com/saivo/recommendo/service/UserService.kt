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

  fun save(user: User, action: String = ""): Response {
    return when (action) {
      "UPDATE" -> update(user)
      "RESET_PASSWORD" -> resetPassword(user)
      else -> register(user)
    }
  }

  private fun register(user: User): Response {
    return Response().apply {
      with(userRepository) {
        findUserByEmail(user.email).let {
          if (it?.email == null) {
            save(user.encode(::passwordEncoder)).apply {
              data = id
              status = "REGISTRATION_SUCCESSFUL"
              message = "$firstname $lastname Registered with $email"
            }
          } else {
            error = "EMAIL_TAKEN"
            status = "REGISTRATION_UNSUCCESSFUL"
            message = "Can't Register more accounts"
          }
        }
      }
    }
  }

  private fun resetPassword(user: User): Response {
    return Response().apply {
      with(userRepository) {
        save(user.encode(::passwordEncoder))
      }.let {
        data = it.id
        status = "RESET_USER_PASSWORD"
        message = "${it.firstname} ${it.lastname} you have reset your password"
      }
    }
  }

  private fun update(user: User): Response {
    return Response().apply {
      with(userRepository) {
        save(user.updated(user))
      }.let {
        data = it.id
        status = "USER_UPDATED"
        message = "${it.firstname} ${it.lastname} you have updated your information"
      }
    }
  }

  fun getUserById(Id: String): User {
    return userRepository.findById(Id).orElseThrow { UserNotFoundException() }
  }

  fun getUsers(): List<User> {
    return userRepository.findAll().toList().apply {
      if (isNullOrEmpty()) {
        throw EmptyResultException()
      }
    }
  }

  override fun loadUserByUsername(email: String): UserDetails {
    return getUserByEmail(email)
  }

  fun getUserByEmail(email: String): User = userRepository
    .findUserByEmail(email).run {
      return this ?: throw UserNotFoundException()
    }

  fun loginUser(login: Login): Response {
    return Response().apply {
      runCatching {
        getUserByEmail(login.email).apply {
          if (checkUserPassword(this, login.password)) {
            return@runCatching this
          }
        }
      }.onSuccess {
        data = it
        status = "LOGIN_SUCCESSFUL"
        message = "${it.firstname} ${it.lastname} has Logged In"
      }.onFailure {
        status = "LOGIN_UNSUCCESSFUL"
        when (it) {
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
    return encoder.matches(password, user.userPassword).also {
      if (!it) throw BadUserCredentialsException()
    }
  }

  fun passwordEncoder(password: String): String {
    return encoder.encode(password)
  }

  fun sendUserSMS(email: String, number: String): Response {
    return Response().apply {
      runCatching {
        getUserByEmail(email)
      }.onSuccess {
        doSendSms(makeOtp(), number, email)
      }.onFailure {
        data = "ERROR"
        error = "USER_NOT_FOUND"
        message = "User Not Found"
      }
    }
  }

  private fun makeOtp(): String {
    var otp = ""
    for (i in 1..5) {
      otp += Random.nextInt(1, 9)
    }
    return otp
  }

  private fun Response.doSendSms(otp: String, number: String, email: String) {
    runCatching {
      TwilioServer.sendSms("You're PIN: $otp", number)
    }.onSuccess {
      data = otp
      status = "SENT_OTP"
      message = "Sent the OTP for $email"
    }.onFailure {
      data = "ERROR"
      error = "INVALID_NUMBER"
      message = "Number $number is not a valid phone number"
    }
  }
}
