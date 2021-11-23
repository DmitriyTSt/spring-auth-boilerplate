package ru.dmitriyt.springboilerplate.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.dmitriyt.springboilerplate.entity.UserEntity

class CustomUserDetails(
    private val _login: String,
    private val _password: String,
    private val grantedAuthorities: MutableCollection<out GrantedAuthority>,
) : UserDetails {

    companion object {
        fun fromUserEntityToCustomUserDetails(userEntity: UserEntity): CustomUserDetails {
            return CustomUserDetails(
                userEntity.login,
                userEntity.password,
                mutableListOf(SimpleGrantedAuthority("ROLE_USER")),
            )
        }
    }

    override fun getAuthorities() = grantedAuthorities

    override fun getPassword() = _password

    override fun getUsername() = _login

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}