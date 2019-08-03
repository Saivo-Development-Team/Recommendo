package com.saivo.recommendo.model

import javax.persistence.*

@Entity
@Table(name = "Activities")
data class Activity(
                      var title: String,
                      var location: String,
                      var description: String
) : ModelWithID()