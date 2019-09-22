package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.domain.Preference
import com.saivo.recommendo.service.PreferenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/preference")
class PreferenceController {
    @Autowired
    val preferenceService: PreferenceService? = null

    @GetMapping("/all")
    fun getPreference(): MutableIterable<Preference>? {
        return preferenceService?.getPreferences()
    }
}