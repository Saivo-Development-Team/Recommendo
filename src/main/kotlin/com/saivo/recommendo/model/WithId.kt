package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class WithId(
        @Id
        @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
        @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        val id: String? = null
)