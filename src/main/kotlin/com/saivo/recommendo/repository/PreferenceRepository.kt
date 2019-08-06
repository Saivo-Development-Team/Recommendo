package com.saivo.recommendo.repository

import com.saivo.recommendo.model.domain.Preference
import org.springframework.data.repository.CrudRepository

interface PreferenceRepository: CrudRepository<Preference, String>