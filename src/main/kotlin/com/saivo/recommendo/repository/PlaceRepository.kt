package com.saivo.recommendo.repository

import com.saivo.recommendo.model.objects.Place
import org.springframework.data.repository.CrudRepository

interface PlaceRepository: CrudRepository<Place, String>