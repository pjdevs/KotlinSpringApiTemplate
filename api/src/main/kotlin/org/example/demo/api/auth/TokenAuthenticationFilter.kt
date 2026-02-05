package org.example.demo.api.auth

import org.example.demo.domain.ports.TokenService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class TokenAuthenticationFilter(private val tokenService: TokenService) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange)

        if (token != null) {
            val user = tokenService.validateToken(token)

            if (user != null) {
                val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it}") }
                val authentication = UsernamePasswordAuthenticationToken(
                    user.userName,
                    null,
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