package de.cyanbaz.nucleus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class NucleusApplication

fun main(args: Array<String>) {
    runApplication<NucleusApplication>(*args)
}
