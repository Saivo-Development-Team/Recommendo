package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.service.UserService
import com.saivo.recommendo.util.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    val userService: UserService? = null

    @PostMapping("/add")
    fun addUser(@RequestBody user: User) = userService!!.saveUser(user)

    @GetMapping("/all")
    fun getUsers(): List<User> = userService!!.getUsers()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): Response {
        return try {
            Response(data = userService!!.getUserById(id), status = "USER_FOUND")
        } catch (e: UserNotFoundException) {
            Response(error = "USER_NOT_FOUND", status = "USER_ERROR", message = e.message!!)
        }
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody user: User):
            Response = userService!!.saveUser(user, "update")

    @PostMapping("/register")
    @PreAuthorize("#oauth2.hasScope('register')")
    fun registerUser(@RequestBody user: User): Response = userService!!.saveUser(user)

    @PostMapping("/login")
    fun loginUser(@RequestBody login: Login): Response = userService!!.loginUser(login)

}