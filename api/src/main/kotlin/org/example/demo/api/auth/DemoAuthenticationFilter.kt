package org.example.demo.api.auth

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

data class User(val username: String, val password: String, val role: String)

@Component
class DemoAuthenticationFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange)

        if (token != null) {
            val user = when (token) {
                "admin" -> User("admin", "adminPassword", "ADMIN")
                "user" -> User("user", "userPassword", "USER")
                else -> null
            }

            if (user != null) {
                val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
                val authentication = UsernamePasswordAuthenticationToken(
                    user.username,
                    user.password,
                    authorities
                )

                return chain
                    .filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
            }
        }

        return chain.filter(exchange)
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (authHeader?.startsWith("Bearer ") == true) authHeader.substring(7) else null
    }
}