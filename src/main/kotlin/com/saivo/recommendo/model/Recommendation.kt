package com.saivo.recommendo.model


import javax.persistence.*

@Entity
@Table(name = "Recommendations")
data class Recommendation(
        @OneToOne var activity: Activity
) : ModelWithID()