package com.saivo.recommendo.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Ratings")
class Rating : WithId()