package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*
import javax.persistence.CascadeType.*

@Entity
@Table(name = "recommendation")
data class Recommendation(
        @OneToOne(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Activity::class)
        val activity: Activity,
        val date: Timestamp
) : WithId(), Serializable