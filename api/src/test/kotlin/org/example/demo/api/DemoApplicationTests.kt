package org.example.demo.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class DemoApplicationTests {

    @Configuration
    class TestConfig

    @Test
    fun contextLoads() {
    }

}