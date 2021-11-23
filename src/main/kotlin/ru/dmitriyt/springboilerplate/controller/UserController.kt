package ru.dmitriyt.springboilerplate.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dmitriyt.springboilerplate.entity.UserEntity
import ru.dmitriyt.springboilerplate.service.UserService

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
    private val userService: UserService,
) {
    @GetMapping("/")
    fun getUsers(): List<UserEntity> {
        return userService.getUsers()
    }
}