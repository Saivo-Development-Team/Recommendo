package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Preferences")
class Preference(@Id
                 @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
                 @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
                 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                 var id: String? = null,
                 var Likes: String
)