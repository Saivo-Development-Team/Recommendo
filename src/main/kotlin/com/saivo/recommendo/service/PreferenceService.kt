package com.saivo.recommendo.service

import com.saivo.recommendo.model.domain.Option
import com.saivo.recommendo.model.domain.Preference
import com.saivo.recommendo.model.infrastructure.Opinion
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PreferenceService {

  @Autowired
  lateinit var preferenceRepository: PreferenceRepository

  @Autowired
  lateinit var rLikes: LikeRepository

  @Autowired
  lateinit var rDislikes: DislikeRepository

  @Autowired
  lateinit var rCategories: CategoryRepository

  @Autowired
  lateinit var userService: UserService

  fun getPreference(Id: String): Response {
    return Response().apply {
      runCatching {
        preferenceRepository.findById(Id).get()
      }.onSuccess {
        data = it
        status = "SUCCESS"
        message = "Preference with [$Id] Found"
      }
    }
  }

  fun savePreference(email: String, opinion: Opinion): Response {
    with(userService) {
      getUserByEmail(email).apply {
        val preference = options(opinion)
        return save(copy(preferences = preferences?.plus(preference)), "UPDATE")
      }
    }
  }

  private fun options(opinion: Opinion): Preference {
    return Opinion.invoke(opinion).let {
      return@let it.copy(
        likes = rLikes.findByValueEquals(it.likes.value) ?: it.likes,
        dislikes = rDislikes.findByValueEquals(it.dislikes.value) ?: it.dislikes,
        category = rCategories.findByNameEquals(it.category.name) ?: it.category
      )
    }
  }

  fun getPreferences(email: String): Response {
    return Response().apply {
      runCatching {
        userService.getUserByEmail(email).preferences
      }.onSuccess {
        data = (it ?: setOf()).toOpinions()
        status = "SUCCESS"
        message = "Got Preferences for User with [$email]"
      }
    }
  }

  fun Set<Preference>.toOpinions(): List<Opinion> {
    return this.map {
      it.toOpinion()
    }
  }

  fun Preference.toOpinion(): Opinion {
    return Opinion(
      id = this.id,
      likes = this.likes.value,
      dislikes = this.dislikes.value,
      category = this.category.name,
      description = this.description
    )
  }

  fun Opinion.toPreference(): Preference {
    return Preference(
      likes = Option.Like(this.likes),
      dislikes = Option.Dislike(this.dislikes),
      category = Option.Category(this.category),
      description = this.description
    )
  }
}
