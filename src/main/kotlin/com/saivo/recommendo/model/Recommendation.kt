package com.saivo.recommendo.model


import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "Recommendations")
data class Recommendation(
        @OneToOne var activity: Activity
) : WithId()