package ru.dmitriyt.springboilerplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.dmitriyt.springboilerplate.config.property.JwtSettings

@SpringBootApplication
class SpringAuthBoilerplateApplication

fun main(args: Array<String>) {
    runApplication<SpringAuthBoilerplateApplication>(*args)
}
