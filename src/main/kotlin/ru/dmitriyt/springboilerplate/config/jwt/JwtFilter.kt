package ru.dmitriyt.springboilerplate.config.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import ru.dmitriyt.springboilerplate.config.CustomUserDetailsService
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtFilter @Autowired constructor(
    private val jwtProvider: JwtProvider,
    private val customUserDetailsService: CustomUserDetailsService,
) : GenericFilterBean() {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = getTokenFromRequest(request as HttpServletRequest?)
        if (token != null && jwtProvider.validateToken(token)) {
            val login = jwtProvider.getLoginFromToken(token)
            val customUserDetails = customUserDetailsService.loadUserByUsername(login)
            val auth = UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        chain?.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest?): String? {
        val bearer = request?.getHeader(AUTHORIZATION)
        if (!bearer.isNullOrEmpty() && bearer.startsWith("Bearer ")) {
            return bearer.substring(7)
        }
        return null
    }
}