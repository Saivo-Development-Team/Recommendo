package com.saivo.recommendo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean


@SpringBootApplication
class RecommendoServer

fun main(args: Array<String>) {
    runApplication<RecommendoServer>(*args)
}