package com.saivo.recommendo.model.domain

import com.saivo.recommendo.model.infrastructure.WithId
import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "Ratings")
class Rating (
        @OneToOne
        val user: User,
        val stars: Int = 0,
        val comment: String = "",
        val writen: Timestamp
): WithId()