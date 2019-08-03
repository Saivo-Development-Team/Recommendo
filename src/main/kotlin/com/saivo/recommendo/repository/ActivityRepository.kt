package com.saivo.recommendo.repository

import com.saivo.recommendo.model.Activity
import org.springframework.data.repository.CrudRepository

interface ActivityRepository: CrudRepository<Activity, String>