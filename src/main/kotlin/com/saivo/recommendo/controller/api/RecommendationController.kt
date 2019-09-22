package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.repository.PlaceRepository
import com.saivo.recommendo.service.ActivityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recommendation")
class RecommendationController {
    @Autowired
    val activityService: ActivityService? = null

    @Autowired
    val placeRepository: PlaceRepository? = null

    @GetMapping("/activities")
    fun getUserActivities(@PathVariable email: String): ArrayList<Activity>? {
        return activityService?.getAllActivities(email)
    }
    
}