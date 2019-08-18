package com.saivo.recommendo.model.objects

data class Response (
    var data: Any? = null,
    var error: String = "NO_ERROR",
    var status: String = "NOTHING",
    var message: String = "EMPTY"
)