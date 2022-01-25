package ru.dmitriyt.springboilerplate.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.dmitriyt.springboilerplate.entity.AnonymousProfileEntity

interface AnonymousProfileRepository : JpaRepository<AnonymousProfileEntity, Long> {
    fun findByDeviceId(deviceId: String): AnonymousProfileEntity?
    fun findByDeviceIdAndIsActiveIsTrue(deviceId: String): AnonymousProfileEntity?
}