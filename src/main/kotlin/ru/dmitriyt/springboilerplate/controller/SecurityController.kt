package ru.dmitriyt.springboilerplate.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import ru.dmitriyt.springboilerplate.config.jwt.JwtProvider
import ru.dmitriyt.springboilerplate.controller.model.LoginRequest
import ru.dmitriyt.springboilerplate.controller.model.RegistrationRequest
import ru.dmitriyt.springboilerplate.controller.model.TokenResponse
import ru.dmitriyt.springboilerplate.entity.UserEntity
import ru.dmitriyt.springboilerplate.service.UserService

@RestController
class SecurityController @Autowired constructor(
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): TokenResponse {
        val userEntity = userService.findByLoginAndPassword(request.login, request.password)
        return userEntity
            ?.let { jwtProvider.generateToken(request.login) }
            ?.let { TokenResponse(it) }
            ?: throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/registration")
    fun registration(@RequestBody request: RegistrationRequest) {
        if (request.login == null || request.password == null) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "login or password is empty")
        }
        val userEntity = UserEntity(login = request.login, password = request.password)
        userService.save(userEntity)
    }
}