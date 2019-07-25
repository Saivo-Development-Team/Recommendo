package com.saivo.recommendo.service

import com.saivo.recommendo.model.Preference
import com.saivo.recommendo.repository.PreferenceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PreferenceService {

    @Autowired
    val preferenceRepository: PreferenceRepository? = null

    fun addPreference(preference: Preference){
        preferenceRepository!!.save(preference)
    }

}