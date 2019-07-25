package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Users")
@Getter
@Setter
class User(
        @Id
        @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
        @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        var id: String? = null,

        var email: String,
        var username: String,
        var lastname: String,
        var firstname: String,

        @OneToMany @OrderColumn var preferences: Set<Preference>,
        @OneToMany @OrderColumn var recommendations: Set<Recommendation>
)