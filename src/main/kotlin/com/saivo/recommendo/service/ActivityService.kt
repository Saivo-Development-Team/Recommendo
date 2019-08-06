package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.Activity
import com.saivo.recommendo.repository.ActivityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivityService {

    @Autowired
    val activityRepository: ActivityRepository? = null

    fun addActivity(activity: Activity) {
        activityRepository!!.save(activity)
    }

    fun addActivities(activities: ArrayList<Activity>) {
        activities.forEach { activity -> activityRepository!!.save(activity) }
    }
}