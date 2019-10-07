package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.model.domain.Notification
import com.saivo.recommendo.model.domain.Rating
import com.saivo.recommendo.service.ActivityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class RecommendationsController {
    @Autowired
    val activityService: ActivityService? = null

    @GetMapping("/user/{email}/activities")
    fun getUserActivities(@PathVariable email: String): ArrayList<Activity>? {
        return activityService?.getAllActivities(email)
    }

    @GetMapping("/user/{email}/activities/{title}")
    fun getOneUserActivity(@PathVariable email: String, @PathVariable title: String): ArrayList<Activity>? {
        return activityService?.getOneActivity(email)
    }

    @GetMapping("/user/{email}/activities/ratings")
    fun getUserActivitiesRatings(@PathVariable email: String): ArrayList<Rating>? {
        return activityService?.getAllActivityRating(email)
    }

    @GetMapping("/user/{email}/activities/{title}/rating")
    fun getActivityRating(@PathVariable email: String, @PathVariable title: String): ArrayList<Rating>? {
        return activityService?.getActivityRating(email)
    }

    @GetMapping("/user/{email}/recommendations/activities")
    fun getNotifications(@PathVariable email: String): ArrayList<Notification> {
        val data = arrayListOf<Notification>()

        activityService?.allActivities()?.forEach {
            it.apply {
                val _rating = Random.nextDouble(5.0)
                data.add(Notification(
                        rated = "Based off a previous activity",
                        title = title,
                        rating = _rating.toString(),
                        users = Random.nextInt(100),
                        stars = _rating,
                        image = ""
                ))
            }
        }
        return data
    }
}