package com.saivo.recommendo.service

import com.saivo.recommendo.model.Recommendation
import com.saivo.recommendo.repository.RecommendationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecommendationService {

    @Autowired
    val recommendationRepository: RecommendationRepository? = null

    fun addRecommendation(recommendation: Recommendation) {
        recommendationRepository!!.save(recommendation)
    }
}