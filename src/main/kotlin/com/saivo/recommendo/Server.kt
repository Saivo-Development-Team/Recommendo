package com.saivo.recommendo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
class Server

fun main(args: Array<String>) {
	runApplication<Server>(*args)
}
