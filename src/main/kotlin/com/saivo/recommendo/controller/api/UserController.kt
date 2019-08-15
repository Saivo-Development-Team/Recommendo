package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.service.UserService
import com.saivo.recommendo.util.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    @Autowired
    val userService: UserService? = null

    @GetMapping("/api/users")
    fun getUsers(): List<User> = userService!!.getUsers()

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: String) : User {
        return try {
            userService!!.getUserById(id)
        } catch (e: UserNotFoundException) {
            print(e.message)
            e.message
        } as User
    }

    @ResponseBody
    @PutMapping("/api/users/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody user: User):
            String? = userService!!.saveUser(user, "update")

    @PostMapping("/users/register")
    @PreAuthorize("#oauth2.hasScope('register')")
    fun registerUser(@RequestBody user: User): String? = userService!!.saveUser(user)

    @PostMapping("/users/login")
    fun loginUser(@RequestBody login: Login): User? = userService!!.loginUser(login)

}