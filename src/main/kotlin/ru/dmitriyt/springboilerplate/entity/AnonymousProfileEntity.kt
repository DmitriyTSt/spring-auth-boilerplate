package ru.dmitriyt.springboilerplate.entity

import ru.dmitriyt.springboilerplate.dto.enums.Os
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "anonymous_users")
class AnonymousProfileEntity(
    @Id
    val id: Long = 0,
    val deviceId: String,
    var os: Os,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)