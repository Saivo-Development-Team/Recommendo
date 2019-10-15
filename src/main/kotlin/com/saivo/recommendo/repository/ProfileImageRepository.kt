package com.saivo.recommendo.repository

import com.saivo.recommendo.model.objects.ProfileImage
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.RestController

interface ProfileImageRepository: CrudRepository<ProfileImage, String>