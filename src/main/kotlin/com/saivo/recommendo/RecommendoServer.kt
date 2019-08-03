package com.saivo.recommendo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecommendoServer

fun main(args: Array<String>) {
	runApplication<RecommendoServer>(*args)
}
