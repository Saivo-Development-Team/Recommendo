package com.saivo.recommendo.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Activates")
class Activity (        @Id
                        @GeneratedValue(generator="system-uuid")
                        @GenericGenerator(name="system-uuid", strategy = "uuid")
                        var id: String? = null
)