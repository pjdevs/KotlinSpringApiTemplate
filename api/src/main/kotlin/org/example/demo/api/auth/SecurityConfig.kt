package org.example.demo.api.auth

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.example.demo.domain.ports.PasswordHasher
import org.example.demo.domain.ports.TokenService
import org.example.demo.domain.ports.UserRepository
import org.example.demo.domain.usecases.LoginUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@SecurityScheme(name = "App Bearer Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun tokenService(
        @Value($$"${jwt.secret}") secret: String,
        @Value($$"${jwt.expiration:3600000}") expiration: Long // 1h default
    ): TokenService = JwtTokenService(secret, expiration)

    @Bean
    fun tokenAuthenticationFilter(tokenService: TokenService) = TokenAuthenticationFilter(tokenService)

    @Bean
    fun securityFilterChain(
        http: ServerHttpSecurity,
        authenticationFilter: TokenAuthenticationFilter,
    ): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange { spec ->
                spec
                    // User endpoints
                    .pathMatchers("/v1/videos/{videoRef}/react").authenticated()
                    // Admin only
                    .pathMatchers(HttpMethod.POST, "/v1/videos/add").hasRole("ADMIN")
                    // Otherwise must ok
                    .anyExchange().permitAll()
            }
            .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()

    @Bean
    fun passwordHasher(encoder: PasswordEncoder): PasswordHasher = SecurityPasswordHasher(encoder)

    @Bean
    fun loginUseCase(
        userRepository: UserRepository,
        tokenService: TokenService,
        passwordHasher: PasswordHasher,
    ) = LoginUseCase(
        userRepository,
        tokenService,
        passwordHasher,
    )
}