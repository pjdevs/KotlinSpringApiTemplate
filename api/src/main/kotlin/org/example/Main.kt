package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

interface Base {
    fun printMessage()
    fun printMessageLine()
    fun <T> transformSomething(something: T, callback: (T) -> T) : T
}

class BaseImpl(val x: Int) : Base {
    override fun printMessage() { print(x) }
    override fun printMessageLine() { println(x) }
    override fun <T> transformSomething(something: T, callback: (T) -> T) : T = callback(something)
}

class Derived(b: Base) : Base by b {
    override fun printMessage() { print("abc") }
}

suspend fun main(args: Array<String>) {
    val base = BaseImpl(10)
    val derived = Derived(base)
    derived.printMessage()
    derived.printMessageLine()

    val cb = { s: Int -> 2 * s }
    val res = derived.transformSomething(2) { s -> cb(s) }
    println(res)

    coroutineScope {
        val dres = async { superRoutine() }
        runApplication<DemoApplication>(*args)
    }
}

suspend fun superRoutine() {
    for (i in 1..10) {
        println(i)
        delay(1000)
    }
}

@SpringBootApplication
class DemoApplication

@RestController
class HelloController(private val sentenceService: SentenceService) {
    @GetMapping("/")
    fun hello(name: String?) : String {
        val sentence = sentenceService.getSentence()
        return "Hello $name! This a cool sentence '$sentence'."
    }
}

@Configuration
class DemoConfig {
    @Bean
    fun sentenceService() : SentenceService = RandomSentenceService()
}

interface SentenceService {
    fun getSentence(): String
}

class RandomSentenceService : SentenceService {
    override fun getSentence(): String = arrayOf("Easy pizzy...", "I am a hero.", "Look back at it!").random()
}