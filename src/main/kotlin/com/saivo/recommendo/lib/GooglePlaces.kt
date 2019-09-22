package com.saivo.recommendo.lib

import com.google.maps.*

object GooglePlaces {

    private final val geoApiContext: GeoApiContext = GeoApiContext.Builder().apiKey(System.getenv("GOOGLE_API_KEY")).build()

    fun searchPlaceByText(text: String): TextSearchRequest? {
        return PlacesApi.textSearchQuery(geoApiContext, text)
    }
}