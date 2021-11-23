package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.dmitriyt.springboilerplate.entity.UserEntity

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
}