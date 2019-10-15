package com.saivo.recommendo.repository

import com.saivo.recommendo.model.domain.Option
import org.springframework.data.repository.CrudRepository

interface LikeRepository : CrudRepository<Option.Like, String> {
  fun findByValueEquals(value: String): Option.Like?
}

interface DislikeRepository : CrudRepository<Option.Dislike, String> {
  fun findByValueEquals(value: String): Option.Dislike?
}

interface CategoryRepository : CrudRepository<Option.Category, String> {
  fun findByNameEquals(name: String): Option.Category?
}
