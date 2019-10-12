package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.model.objects.ProfileImage
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.service.ProfileImageService
import com.saivo.recommendo.service.UserService
import com.saivo.recommendo.util.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var profileImageService: ProfileImageService

    @PostMapping("/add")
    fun addUser(@RequestBody user: User) = userService.save(user)

    @GetMapping("/all")
    fun getUsers(): List<User> = userService.getUsers()

    @GetMapping("/{Id}")
    fun getUser(@PathVariable Id: String): Response {
        return try {
            Response(data = userService.getUserById(Id), status = "USER_FOUND")
        } catch (e: UserNotFoundException) {
            Response(error = "USER_NOT_FOUND", status = "USER_ERROR", message = e.message!!)
        }
    }

    @ResponseBody
    @PutMapping("/update/{Id}")
    fun updateUser(@PathVariable Id: String, @RequestBody user: User) = userService.save(user, "UPDATE")

    @PostMapping("/login")
    fun loginUser(@RequestBody login: Login) = userService.loginUser(login)

    @PostMapping("/register")
    @PreAuthorize("#oauth2.hasScope('register')")
    fun registerUser(@RequestBody user: User) = userService.save(user)

    @PostMapping("/reset-password/{email}")
    fun resetUserPassword(@RequestBody password: String, @PathVariable email: String) =
            userService.save(userService.getUserByUsername(email).copy(userPassword = password), "RESET_PASSWORD")

    @PostMapping("/otp/{email}")
    fun getSendUserOTP(@RequestBody number: String, @PathVariable email: String) = userService.sendUserSMS(email, number)

    @PostMapping("/upload/{Id}/profile")
    fun uploadUserImage(@RequestParam("image") image: MultipartFile, @PathVariable Id: String) {
        profileImageService.saveUserImage(Id, ProfileImage(
                data = image.bytes,
                fileName = image.originalFilename ?: "${Id}.jpeg"
        ))
    }

}