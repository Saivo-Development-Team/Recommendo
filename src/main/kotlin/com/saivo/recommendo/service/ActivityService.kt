package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.model.domain.Rating
import com.saivo.recommendo.repository.ActivityRepository
import com.saivo.recommendo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityService {

    @Autowired
    val activityRepository: ActivityRepository? = null
    @Autowired
    val userRepository: UserRepository? = null
    fun allActivities() = activityRepository?.findAll()
    fun addActivity(activity: Activity) {
        activityRepository!!.save(activity)
    }

    fun addActivities(activities: ArrayList<Activity>) {
        activities.forEach { activity -> activityRepository!!.save(activity) }
    }

    fun getAllActivities(email: String): ArrayList<Activity> {
        var activities = ArrayList<Activity>()
        userRepository?.findUserByEmail(email)?.recommendations?.forEach { activities.add(it.activity) }
        return activities
    }

    fun getOneActivity(email: String): ArrayList<Activity> {
        var oneActivity = ArrayList<Activity>()
        userRepository?.findUserByEmail(email)?.recommendations?.forEach {it.activity.title}
        return oneActivity
    }

    fun getAllActivityRating(email: String): ArrayList<Rating> {
        val ratings = ArrayList<Rating>()
        userRepository?.findUserByEmail(email)?.recommendations?.forEach {it.activity.ratings.forEach{ratings.add(it)}}
        return ratings
    }

    fun getActivityRating(email: String): ArrayList<Rating> {
        val rating = ArrayList<Rating>()
        userRepository?.findUserByEmail(email)?.recommendations?.forEach {it.activity.ratings.forEach{rating.add(it)}}
        return rating
    }

}