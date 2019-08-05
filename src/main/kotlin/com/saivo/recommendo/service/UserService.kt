package com.saivo.recommendo.service

import com.saivo.recommendo.model.AuthUser
import com.saivo.recommendo.model.User
import com.saivo.recommendo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService: UserDetailsService {


    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    fun getUsers(): List<User> {
        val users: ArrayList<User> = ArrayList()
        userRepository!!.findAll().forEach { user -> users.add(user) }
        return users
    }


    override fun loadUserByUsername(username: String?): UserDetails {
        return UserDetailsService {
            AuthUser(userRepository!!.findUserByUsername(username!!)!!)
        }.loadUserByUsername(username)
    }

    fun getUser(id: String): User {
        return userRepository!!.findById(id).get()
    }

    fun addUser(user: User) {
        userRepository!!.save(user.apply {
            this.password = encoder!!.encode(user.password)
        })
    }

    fun deleteUser(id: String) {
        userRepository!!.deleteById(id)
    }
}
