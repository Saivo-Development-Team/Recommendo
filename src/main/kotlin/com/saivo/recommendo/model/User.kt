package com.saivo.recommendo.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "Users")
class User(
        @Id
        @GeneratedValue(generator="system-uuid")
        @GenericGenerator(name="system-uuid", strategy = "uuid")
        var id: String? = null,
        var email: String,
        var username: String,
        var lastname: String,
        var firstname: String,
        @OneToMany @OrderColumn var preference: Array<Preference>
)