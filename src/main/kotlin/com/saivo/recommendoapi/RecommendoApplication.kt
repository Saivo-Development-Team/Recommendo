package com.saivo.recommendoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecommendoApplication

fun main(args: Array<String>) {
	runApplication<RecommendoApplication>(*args)
}
