package com.saivo.recommendo.model

import javax.persistence.*


@Entity
@Table(name = "Roles")
class Role(

        @Column(name = "role")
        val role_type: String? = null // e.g USER, ADMIN, CLIENT, id: String?

) : ModelWithID()