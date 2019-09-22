package com.saivo.recommendo.model.objects

import com.google.maps.model.Geometry
import com.google.maps.model.OpeningHours
import com.google.maps.model.Photo
import com.google.maps.model.PlaceIdScope
import java.net.URL
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "place")
data class Place(
        @Id
        var placeId: String? = null,
        var formattedAddress: String? = null,
        var geometry: Geometry? = null,
        var name: String? = null,
        var icon: URL? = null,
        var scope: PlaceIdScope? = null,
        var rating: Float = 0.toFloat(),
        var types: Array<String>? = null,
        var openingHours: OpeningHours? = null,
        var photos: Array<Photo>? = null,
        var vicinity: String? = null,
        var permanentlyClosed: Boolean = false,
        var userRatingsTotal: Int = 0)