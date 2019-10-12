package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.Preference
import com.saivo.recommendo.repository.PreferenceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PreferenceService {

    @Autowired
    lateinit var preferenceRepository: PreferenceRepository

    fun addPreference(preference: Preference){
        preferenceRepository.save(preference)
    }

    fun getPreferences(): MutableIterable<Preference>? {
        return preferenceRepository.findAll()
    }

}