package com.skrypnik.financy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinancyApplication

fun main(args: Array<String>) {
	runApplication<FinancyApplication>(*args)
}
