package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.*

@Entity
@Table(name = "Activities")
data class Activity(
                      @OrderColumn
                      @OneToMany(fetch = FetchType.EAGER)
                      val ratings: Set<Rating>,
                      val title: String,
                      val location: String,
                      val category: String,
                      val description: String
) : WithId()