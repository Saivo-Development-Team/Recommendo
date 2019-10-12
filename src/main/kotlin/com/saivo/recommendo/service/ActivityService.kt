package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.repository.ActivityRepository
import com.saivo.recommendo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityService {

    @Autowired
    private lateinit var activityRepository: ActivityRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    fun addActivity(activity: Activity) = activityRepository.save(activity)

    fun addActivities(activities: ArrayList<Activity>) {
        activities.forEach { activity -> activityRepository.save(activity) }
    }

    fun getAllActivities(email: String): ArrayList<Activity> {
        val activities = ArrayList<Activity>()
        userRepository.findUserByEmail(email)?.recommendations?.forEach {
            activities.add(it.activity)
            println("${it.activity}")
        }
        println("getAllActivities: Returned: [$activities]")
        return activities
    }
}