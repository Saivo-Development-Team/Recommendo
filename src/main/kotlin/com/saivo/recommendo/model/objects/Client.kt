package com.saivo.recommendo.model.objects

data class Client(
    val accessTokenValidity: Int,
    val authorizedGrantTypes: String ,
    val clientId: String ,
    val clientSecret: String,
    val refreshTokenValidity: Int,
    val resourceIds: String,
    val scope: String 
)