package com.saivo.recommendo.model.infrastructure

import com.saivo.recommendo.model.domain.Option
import com.saivo.recommendo.model.domain.Preference

data class Opinion(
  val id: String,
  val likes: String,
  val dislikes: String,
  val category: String,
  val description: String
) {
  companion object {
    operator fun invoke(opinion: Opinion): Preference {
      return Preference(
        likes = Option.Like(opinion.likes),
        dislikes = Option.Dislike(opinion.dislikes),
        category = Option.Category(opinion.category),
        description = opinion.description
      )
    }
  }
}