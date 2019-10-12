package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.domain.Preference
import com.saivo.recommendo.service.PreferenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/preference")
class PreferenceController {
  @Autowired
  lateinit var preferenceService: PreferenceService

  @GetMapping("/all")
  fun getPreference(): MutableIterable<Preference>? {
    return preferenceService.getPreferences()
  }

  @PostMapping("/add")
  fun addPreference(@RequestBody preference: Preference) {
    preferenceService.addPreference(preference)
  }
}