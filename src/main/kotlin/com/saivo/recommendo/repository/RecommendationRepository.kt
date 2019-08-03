package com.saivo.recommendo.repository

import com.saivo.recommendo.model.Recommendation
import org.springframework.data.repository.CrudRepository

interface RecommendationRepository: CrudRepository<Recommendation,String> {

}