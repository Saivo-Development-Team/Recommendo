package com.saivo.recommendo.model.objects

data class Response (
    var data: Any? = null,
    var error: String = "",
    var status: String = "",
    var message: String = ""
)