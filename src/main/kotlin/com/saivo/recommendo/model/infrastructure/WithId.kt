package com.saivo.recommendo.model.infrastructure

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class WithId(
        @Id
        @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
        @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
        val id: String = ""
): Serializable