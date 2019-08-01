package com.saivo.recommendo.controller

import com.saivo.recommendo.model.User
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

    @GetMapping("/api/users")
    fun getUsers(): List<User> {
        return userService!!.getUsers()
    }

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
        userService!!.addUser(user)
        return user.id
    }

    @GetMapping("/api/users/{id}")
    fun getUser(@PathVariable id: String) = userService!!.getUser(id)

}