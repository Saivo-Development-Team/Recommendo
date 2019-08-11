package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "preference")
data class Preference(
        @Size(min = 3, max = 120)
        @Column(name = "prefer", unique = true)
        val prefer: String,

        @Size(min = 3, max = 120)
        @Column(name = "disfavor", unique = true)
        val disfavor: String,

        @Size(max = 500)
        val description: String
) : WithId()