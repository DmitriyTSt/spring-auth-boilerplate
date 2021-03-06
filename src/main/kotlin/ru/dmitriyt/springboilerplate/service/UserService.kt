package ru.dmitriyt.springboilerplate.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.dmitriyt.springboilerplate.entity.UserEntity
import ru.dmitriyt.springboilerplate.repository.UserProfileRepository

@Service
class UserService @Autowired constructor(
    private val repository: UserProfileRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun create(userEntity: UserEntity): UserEntity {
        userEntity.password = passwordEncoder.encode(userEntity.password)
        return repository.save(userEntity)
    }

    fun save(userEntity: UserEntity): UserEntity {
        return repository.save(userEntity)
    }

    fun findByLogin(login: String): UserEntity? {
        return repository.findByLogin(login)
    }

    fun findByLoginAndPassword(login: String, password: String): UserEntity? {
        val userEntity = findByLogin(login)
        return userEntity?.takeIf { passwordEncoder.matches(password, userEntity.password) }
    }

    fun getUsers(): List<UserEntity> {
        return repository.findAll()
    }
}