package ru.dmitriyt.springboilerplate.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import ru.dmitriyt.springboilerplate.service.UserService

@Component
class CustomUserDetailsService @Autowired constructor(
    private val userService: UserService,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userService.findByLogin(username)
        return userEntity?.let { CustomUserDetails.fromUserEntityToCustomUserDetails(it) }
            ?: throw UsernameNotFoundException("Username '$username' not found")
    }
}