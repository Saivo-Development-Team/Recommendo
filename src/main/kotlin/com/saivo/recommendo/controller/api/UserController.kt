package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.objects.Login
import com.saivo.recommendo.model.domain.User
import com.saivo.recommendo.service.ActivityService
import com.saivo.recommendo.service.PreferenceService
import com.saivo.recommendo.service.RecommendationService
import com.saivo.recommendo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    @Autowired
    val userService: UserService? = null

    @Autowired
    val preferenceService: PreferenceService? = null

    @Autowired
    val recommendationService: RecommendationService? = null

    @Autowired
    val activityService: ActivityService? = null

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: String) = userService!!.getUser(id)

    @GetMapping("/api/users")
    fun getUsers(): List<User> = userService!!.getUsers()

    @ResponseBody
    @PostMapping("/api/users")
    fun addUser(@RequestBody user: User): String? {
        if (user.preferences.isNotEmpty()) user.preferences.forEach { preference ->
            preferenceService!!.addPreference(preference)
        }

        if (user.recommendations.isNotEmpty()) user.recommendations.forEach { recommendation ->
            activityService!!.addActivity(recommendation.activity)
            recommendationService!!.addRecommendation(recommendation)
        }

        return userService!!.addUser(user)
    }

    @PostMapping("/users/register")
    fun registerUser(@RequestBody user: User): String? {
        return addUser(user)
    }

    @PostMapping("/users/login")
    fun loginUser(@RequestBody login: Login): User? {
        return userService!!.loginUser(login)
    }

}