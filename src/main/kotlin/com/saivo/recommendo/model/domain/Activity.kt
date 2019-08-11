package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import javax.validation.constraints.Size
import javax.persistence.*

@Entity
@Table(name = "activity")
data class Activity(

        @Size(max = 160)
        val title: String,
        val location: String,
        val category: String,
        @Size(max = 500)
        val description: String,
        @OrderColumn
        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], targetEntity = Rating::class)
        val ratings: Set<Rating>
) : WithId()