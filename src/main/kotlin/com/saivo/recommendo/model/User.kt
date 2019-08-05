package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "Users")
open class User(user: User?) : WithId(), Serializable {

    val email: String = user!!.email

    @Column(name = "username", nullable = false, unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @get:JvmName("_username")
    val username: String = user!!.username

    val lastname: String = user!!.lastname
    val firstname: String = user!!.firstname

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @get:JvmName("_password")
    var password: String = user!!.password

    val enabled: Boolean = user!!.enabled
    val credentialsExpired: Boolean = user!!.credentialsExpired
    val accountExpired: Boolean = user!!.accountExpired
    val accountLocked: Boolean = user!!.accountLocked

    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn
    val roles: Set<Role> = user!!.roles

    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn
    val preferences: Set<Preference> = user!!.preferences

    @OneToMany(fetch = FetchType.EAGER)
    @OrderColumn
    val recommendations: Set<Recommendation> = user!!.recommendations
}