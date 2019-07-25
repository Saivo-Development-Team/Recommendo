package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "Recommendations")
class Recommendation(
        @Id
        @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
        @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        var id: String? = null,
        @OneToOne var activity: Activity
)