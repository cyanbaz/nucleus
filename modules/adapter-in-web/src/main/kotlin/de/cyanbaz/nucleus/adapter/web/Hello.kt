package de.cyanbaz.nucleus.adapter.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Hello {
    @GetMapping("/hello")
    fun hello(): ResponseEntity<Any> = ResponseEntity.ok("Hello World")
}
