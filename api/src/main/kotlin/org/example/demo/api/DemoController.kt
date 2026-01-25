package org.example.demo.api

import org.example.demo.domain.usecases.DemoUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController(private val demoUseCase: DemoUseCase) {
    @GetMapping("/")
    suspend fun hello(@RequestParam name: String?) = demoUseCase.sayHello(name)
}