package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.service.ActivityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RecommendationsController {
    @Autowired
    val activityService: ActivityService? = null

    @GetMapping("/user/activities/{email}")
    fun getUserActivities(@PathVariable email: String): ArrayList<Activity>? {
        return activityService?.getAllActivities(email)
    }

}