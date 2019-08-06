package com.saivo.recommendo.model.domain


import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "Recommendations")
data class Recommendation(
        @OneToOne var activity: Activity
) : WithId()