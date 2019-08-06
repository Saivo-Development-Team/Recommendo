package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Preferences")
data class Preference(
        var Likes: String
) : WithId()