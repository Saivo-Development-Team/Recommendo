package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.Opinion
import com.saivo.recommendo.model.infrastructure.WithId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "preference")
data class Preference(
  @OneToOne(
    fetch = FetchType.EAGER,
    cascade = [CascadeType.ALL],
    targetEntity = Option.Like::class
  )
  val likes: Option.Like,
  @OneToOne(
    fetch = FetchType.EAGER,
    cascade = [CascadeType.ALL],
    targetEntity = Option.Dislike::class
  )
  val dislikes: Option.Dislike,
  @OneToOne(
    fetch = FetchType.EAGER,
    cascade = [CascadeType.ALL],
    targetEntity = Option.Category::class
  )
  val category: Option.Category,
  val description: String
) : WithId(), Serializable {
  companion object {
    operator fun invoke(preference: Preference): Opinion {
      return Opinion(
        id = preference.id,
        likes = preference.likes.value,
        dislikes = preference.dislikes.value,
        category = preference.category.name,
        description = preference.description
      )
    }
  }
}