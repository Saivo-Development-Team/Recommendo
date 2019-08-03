package com.saivo.recommendo.service

import com.saivo.recommendo.model.User
import com.saivo.recommendo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService {

    @Autowired
    private val userRepository: UserRepository? = null


    fun getUsers(): List<User> {
        val users: ArrayList<User> = ArrayList()
        userRepository!!.findAll().forEach { user -> users.add(user)}
        return users
    }



    fun getUser(id: String): User {
        return userRepository!!.findById(id).get()
    }

    fun addUser(user: User) {
        userRepository!!.save(user)
    }


    fun deleteUser(id: String) {
        userRepository!!.deleteById(id)
    }
}
