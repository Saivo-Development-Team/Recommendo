package com.saivo.recommendo.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Preferences")
data class Preference(
        var Likes: String
) : WithId()