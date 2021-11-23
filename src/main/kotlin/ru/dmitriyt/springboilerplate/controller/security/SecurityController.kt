package ru.dmitriyt.springboilerplate.controller.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import ru.dmitriyt.springboilerplate.config.jwt.JwtProvider
import ru.dmitriyt.springboilerplate.controller.base.BaseResponse
import ru.dmitriyt.springboilerplate.entity.UserEntity
import ru.dmitriyt.springboilerplate.service.UserService

@RestController
class SecurityController @Autowired constructor(
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
) {

    @PostMapping("/login")
    fun login(@RequestBody @Validated request: LoginRequest): BaseResponse<TokenResponse> {
        val userEntity = userService.findByLoginAndPassword(request.login, request.password)
        return userEntity
            ?.let { jwtProvider.generateToken(request.login) }
            ?.let { BaseResponse(TokenResponse(it)) }
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/registration")
    fun registration(@RequestBody request: RegistrationRequest) {
        if (request.login == null || request.password == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "login or password is empty")
        }
        val userEntity = UserEntity(login = request.login, password = request.password)
        userService.save(userEntity)
    }
}