package com.saivo.recommendo.service

import com.saivo.recommendo.model.objects.ProfileImage
import com.saivo.recommendo.repository.ProfileImageRepository
import com.saivo.recommendo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileImageService {
    @Autowired
    val profileImageRepository: ProfileImageRepository? = null

    @Autowired
    val userRepository: UserRepository? = null

    fun save(profileImage: ProfileImage) {
        profileImageRepository?.save(profileImage)?.fileName
    }

    fun update(profileImage: ProfileImage) = profileImageRepository?.save(profileImage)

    fun saveUserImage(id: String, profileImage: ProfileImage) {
        profileImageRepository?.save(profileImage)
        userRepository?.apply {
            findById(id).ifPresent {
                save(it.copy(
                        profileImageLink = "/user/$id/profile/$profileImage.fileName",
                        profileImage = profileImage)
                )
            }
        }
    }
}