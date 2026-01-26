package org.example.demo.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.example.demo"])
@EntityScan(basePackages = ["org.example.demo"])
class DemoApplication {

//    @Bean
//    fun init(repository: VideoJpaRepository) = CommandLineRunner {
//        repository.save(VideoEntity("youtube", "hbHgIzIbzmQ"))
//        repository.save(VideoEntity("youtube", "sB_zRkqnfd0"))
//        repository.save(VideoEntity("youtube", "oVHpiQdd4iI"))
//    }

}