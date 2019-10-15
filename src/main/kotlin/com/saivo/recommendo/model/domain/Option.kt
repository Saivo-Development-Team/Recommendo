package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity

sealed class Option {
  @Entity
  data class Like(
    @Column(name = "like_value", unique = true)
    val value: String
  ) : WithId(), Serializable

  @Entity
  data class Dislike(
    @Column(name = "dislike_value", unique = true)
    val value: String
  ) : WithId(), Serializable

  @Entity
  data class Category(
    @Column(name = "category_name", unique = true)
    val name: String
  ) : WithId(), Serializable
}