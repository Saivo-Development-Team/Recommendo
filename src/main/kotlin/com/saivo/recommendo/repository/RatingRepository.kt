package com.saivo.recommendo.repository

import com.saivo.recommendo.model.domain.Rating
import org.springframework.data.repository.CrudRepository

interface RatingRepository: CrudRepository<Rating, String>