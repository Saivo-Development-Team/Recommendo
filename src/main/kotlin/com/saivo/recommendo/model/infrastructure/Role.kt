package com.saivo.recommendo.model.infrastructure

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "Roles")
class Role(

        @Column(name = "role")
        val role_type: String? = null // e.g USER, ADMIN, CLIENT, id: String?

) : WithId()