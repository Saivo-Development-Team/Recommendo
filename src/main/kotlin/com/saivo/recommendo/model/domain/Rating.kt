package com.saivo.recommendo.model.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING
import com.saivo.recommendo.model.infrastructure.WithId
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.Size


@Entity
@Table(name = "rating")
data class Rating (
        @Size(min = 6, max = 16)
        val stars: Int = 0,
        @Size(max = 350)
        val comment: String = "",
        @JsonFormat(shape = STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Africa/Johannesburg")
        val written: Timestamp
): WithId(), Serializable