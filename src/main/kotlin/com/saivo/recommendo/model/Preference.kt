package com.saivo.recommendo.model

import javax.persistence.*

@Entity
@Table(name = "Preferences")
data class Preference(
        var Likes: String
) : ModelWithID()