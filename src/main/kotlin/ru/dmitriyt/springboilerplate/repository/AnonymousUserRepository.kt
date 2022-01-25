package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.dmitriyt.springboilerplate.entity.AnonymousUserEntity

interface AnonymousUserRepository : JpaRepository<AnonymousUserEntity, Long>