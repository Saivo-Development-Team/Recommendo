package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.infrastructure.Opinion
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.service.PreferenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/preference")
class PreferenceController {
  @Autowired
  lateinit var preferenceService: PreferenceService

  @GetMapping("/{Id}")
  fun getPreference(@PathVariable("Id") Id: String): Response {
    return preferenceService.getPreference(Id)
  }

  @GetMapping("/all/{email}")
  fun getPreferences(@PathVariable("email") email: String): Response {
    return preferenceService.getPreferences(email)
  }

  @PostMapping("/save/{email}")
  fun addPreference(@PathVariable("email") email: String, @RequestBody opinion: Opinion): Response {
    return preferenceService.savePreference(email, opinion)
  }
}