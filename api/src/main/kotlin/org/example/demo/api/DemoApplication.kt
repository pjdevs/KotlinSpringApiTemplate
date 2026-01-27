package org.example.demo.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@OpenAPIDefinition(
    info = Info(
        title = "Demo Video Reaction API",
        version = "v1",
        description = "Demo API for video reactions"
    )
)
@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.example.demo"])
@EntityScan(basePackages = ["org.example.demo"])
class DemoApplication