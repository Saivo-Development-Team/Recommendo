package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Activities")
data class Activity(
                      var title: String,
                      var location: String,
                      var description: String
) : WithId()