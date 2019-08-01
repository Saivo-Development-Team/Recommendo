package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "Users")
data class User(
        @Id
        @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
        @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        var id: String? = null,

        var email: String,
        @Column(nullable = false, unique = true)
        var username: String,

        var lastname: String,
        var firstname: String,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var password: String,

        @OneToMany @OrderColumn var preferences: Set<Preference>,
        @OneToMany @OrderColumn var recommendations: Set<Recommendation>
)