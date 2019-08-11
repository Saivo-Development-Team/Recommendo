package com.saivo.recommendo.model.infrastructure

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "role")
class Role(
        @Column(name = "role", unique = true, nullable = false, updatable = false)
        val role_type: String? = null // e.g USER, ADMIN, CLIENT, id: String?
) : WithId()