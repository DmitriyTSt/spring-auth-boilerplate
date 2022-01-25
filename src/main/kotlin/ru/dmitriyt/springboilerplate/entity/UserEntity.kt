package ru.dmitriyt.springboilerplate.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: Long = 0,
    val login: String,
    var password: String,
)