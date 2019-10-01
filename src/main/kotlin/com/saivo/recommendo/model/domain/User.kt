package com.saivo.recommendo.model.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.saivo.recommendo.model.infrastructure.Role
import com.saivo.recommendo.model.infrastructure.WithId
import javax.persistence.*
import javax.persistence.CascadeType.*


@Entity
@Table(name = "users")
open class User(
        @Column(name = "email", nullable = false, unique = true)
        open val email: String,
        open val enabled: Boolean,
        open val lastname: String,
        open val firstname: String,
        open val phoneNumber: String = "",
        open val accountNotLocked: Boolean,
        open val accountNotExpired: Boolean,
        open val credentialsNotExpired: Boolean,

        @Column(name = "password", nullable = false)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        open val pass: String,

        @OrderColumn
        @OneToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Role::class)
        open val roles: Set<Role>,

        @OrderColumn
        @ManyToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Preference::class)
        open val preferences: Set<Preference>,

        @OrderColumn
        @ManyToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Recommendation::class)
        open val recommendations: Set<Recommendation>
) : WithId() {

    fun encodeUserPassword(encode: () -> String): User {
        return User(
                email = email,
                roles = roles,
                enabled = enabled,
                pass = encode(),
                lastname = lastname,
                firstname = firstname,
                preferences = preferences,
                accountNotLocked = accountNotLocked,
                accountNotExpired = accountNotExpired,
                recommendations = recommendations,
                credentialsNotExpired = credentialsNotExpired
        )
    }
}